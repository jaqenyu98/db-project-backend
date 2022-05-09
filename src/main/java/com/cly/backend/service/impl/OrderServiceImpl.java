package com.cly.backend.service.impl;

import com.cly.backend.entity.Coupon;
import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.entity.Payment;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.form.OrderForm;
import com.cly.backend.mapper.CouponMapper;
import com.cly.backend.mapper.CustomerVehicleMapper;
import com.cly.backend.mapper.InvoiceMapper;
import com.cly.backend.mapper.PaymentMapper;
import com.cly.backend.service.OrderService;
import com.cly.backend.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CustomerVehicleMapper customerVehicleMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    @Transactional
    public void reserve(Long customerId, OrderForm form) throws BusinessException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pickUpDate = LocalDateTime.parse(form.getPickUpDate(), formatter);
        LocalDateTime dropOffDate = LocalDateTime.parse(form.getDropOffDate(), formatter);
        if (dropOffDate.compareTo(pickUpDate) <= 0)
            throw new BusinessException("Drop off date should be later than pick up date!");
        if (!isVehicleAvailable(form.getVehicleId(), pickUpDate, dropOffDate))
            throw new BusinessException("This vehicle is unavailable.");
        CustomerVehicle order = new CustomerVehicle();
        order.setPickUpLocationId(form.getPickUpLocationId());
        order.setDropOffLocationId(form.getDropOffLocationId());
        order.setPickUpDate(form.getPickUpDate());
        order.setDropOffDate(form.getDropOffDate());
        order.setDailyOdometerLimit(form.getDailyOdometerLimit());
        order.setCustomerId(customerId);
        order.setVehicleId(form.getVehicleId());
        if (form.getCouponId() != null) {
            Long couponId = form.getCouponId();
            Coupon coupon = couponMapper.getCoupon(couponId);
            switch (coupon.getStatus()) {
                case Coupon.VALID:
                    order.setCouponId(form.getCouponId());
                    couponMapper.setStatus(couponId, Coupon.USED);
                    break;
                case Coupon.USED:
                    throw new BusinessException("This coupon has already been used.");
                case Coupon.INVALID:
                    throw new BusinessException("This coupon has expired.");
            }
        }
        order.setStatus(0);
        customerVehicleMapper.reserve(order);
    }

    @Override
    public List<CustomerVehicle> listReservedOrders(Long customerId) {
        return customerVehicleMapper.listReservedOrders(customerId);
    }

    @Override
    public List<CustomerVehicle> listPickedUpOrders(Long customerId) {
        return customerVehicleMapper.listPickedUpOrders(customerId);
    }

    @Override
    public List<CustomerVehicle> listDroppedOffOrders(Long customerId) {
        return customerVehicleMapper.listDroppedOffOrders(customerId);
    }

    @Override
    public List<CustomerVehicle> listPaidOrders(Long customerId) {
        return customerVehicleMapper.listPaidOrders(customerId);
    }

    @Override
    @Transactional
    public void makePayments(Long customerVehicleId, List<Payment> payments) throws BusinessException {
        CustomerVehicle order = customerVehicleMapper.getOrderById(customerVehicleId);
        if (order == null || !order.getCustomerId().equals(ShiroUtils.getId()))
            throw new BusinessException("Please enter the correct customerVehicleId");
        if (order.getStatus()!=CustomerVehicle.DROPPED_OFF)
            throw new BusinessException("The order status must be dropped-off!");
        Long invoiceId = payments.get(0).getInvoiceId();
        BigDecimal invoiceAmount = invoiceMapper.getInvoiceByOrderId(invoiceId).getAmount();
        BigDecimal totalPaymentAmount = BigDecimal.ZERO;
        for (Payment payment : payments) {
            String method = payment.getMethod();
            if (!Payment.METHOD_CREDIT_CARD.equals(method) && !Payment.METHOD_DEBIT_CARD.equals(method) && !Payment.METHOD_GIFT_CARD.equals(method))
                throw new BusinessException("Allowed method input: C, D, and G. C for credit card, D for debit card, and G for gift card.");
            totalPaymentAmount = payment.getAmount().add(totalPaymentAmount);
        }
        if (totalPaymentAmount.compareTo(invoiceAmount) != 0)
            throw new BusinessException("The total payment amount should equal to the invoice amount!");
        for (Payment payment : payments) {
            paymentMapper.insertPayment(payment);
        }
        customerVehicleMapper.setOrderStatus(customerVehicleId, CustomerVehicle.PAID);
    }

    private boolean isVehicleAvailable(Long vehicleId, LocalDateTime pickUpDate, LocalDateTime dropOffDate) {
        List<CustomerVehicle> uncompletedOrders = customerVehicleMapper.listUncompletedOrdersByVehicleId(vehicleId);
        if (uncompletedOrders == null)
            return true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (CustomerVehicle order : uncompletedOrders) {
            LocalDateTime reservedPickUpDate = LocalDateTime.parse(order.getPickUpDate(), formatter);
            LocalDateTime reservedDropOffDate = LocalDateTime.parse(order.getDropOffDate(), formatter);
            //reserved: pick_up_date <= #{dropOffDate} && drop_off_date >= #{pickUpDate}
            if (pickUpDate.compareTo(reservedDropOffDate) <= 0 && dropOffDate.compareTo(reservedPickUpDate) >= 0)
                return false;
        }
        return true;
    }

}

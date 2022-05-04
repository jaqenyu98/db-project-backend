package com.cly.backend.service.impl;

import com.cly.backend.entity.Coupon;
import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.form.OrderForm;
import com.cly.backend.mapper.CouponMapper;
import com.cly.backend.mapper.CustomerVehicleMapper;
import com.cly.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CustomerVehicleMapper customerVehicleMapper;
    @Autowired
    private CouponMapper couponMapper;

    @Override
    @Transactional
    public void reserve(Long customerId, OrderForm form) throws BusinessException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pickUpDate = LocalDateTime.parse(form.getPickUpDate(), formatter);
        LocalDateTime dropOffDate = LocalDateTime.parse(form.getDropOffDate(), formatter);
        if (dropOffDate.compareTo(pickUpDate) <= 0)
            throw new BusinessException("Drop off date should be later than pick up date!");
        if(!isVehicleAvailable(form.getVehicleId(), pickUpDate, dropOffDate))
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
            switch(coupon.getStatus()) {
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

    private boolean isVehicleAvailable(Long vehicleId, LocalDateTime pickUpDate, LocalDateTime dropOffDate) {
        List<CustomerVehicle> uncompletedOrders = customerVehicleMapper.listUncompletedOrdersByVehicleId(vehicleId);
        if (uncompletedOrders == null)
            return true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (CustomerVehicle order: uncompletedOrders) {
            LocalDateTime reservedPickUpDate = LocalDateTime.parse(order.getPickUpDate(), formatter);
            LocalDateTime reservedDropOffDate = LocalDateTime.parse(order.getDropOffDate(), formatter);
            //reserved: pick_up_date <= #{dropOffDate} && drop_off_date >= #{pickUpDate}
            if (pickUpDate.compareTo(reservedDropOffDate)<=0 && dropOffDate.compareTo(reservedPickUpDate)>=0)
                return false;
        }
        return true;
    }

}

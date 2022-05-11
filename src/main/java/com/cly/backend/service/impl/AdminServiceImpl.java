package com.cly.backend.service.impl;

import com.cly.backend.entity.Coupon;
import com.cly.backend.entity.Customer;
import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.form.AdminRegisterForm;
import com.cly.backend.mapper.*;
import com.cly.backend.service.AdminService;
import com.cly.backend.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerVehicleMapper customerVehicleMapper;
    @Autowired
    private CorporateMapper corporateMapper;
    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    @Transactional
    public Long adminRegister(AdminRegisterForm form) throws BusinessException {
        if (customerMapper.checkUsername(form.getUsername()) >= 1)
            throw new BusinessException("Duplicated username!");
        if (customerMapper.checkEmail(form.getEmail()) >= 1)
            throw new BusinessException("Duplicated email!");
        if (customerMapper.checkPhoneNumber(form.getPhoneNumber()) >= 1)
            throw new BusinessException("Duplicated phone number!");
        Customer admin = new Customer();
        admin.setUsername(form.getUsername());
        String salt = ShiroUtils.randomSalt();
        admin.setSalt(salt);
        admin.setPassword(ShiroUtils.Sha256Hash(form.getPassword(), salt));
        admin.setCustomerType(Customer.ADMIN);
        admin.setEmail(form.getEmail());
        admin.setPhoneNumber(form.getPhoneNumber());
        customerMapper.customerRegister(admin);
        return admin.getId();
    }

    @Override
    public void insertCoupon(Coupon coupon) {
        coupon.setStartDate(coupon.getStartDate() + " 00:00:00");
        coupon.setEndDate(coupon.getEndDate() + " 23:59:59");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(coupon.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(coupon.getEndDate(), formatter);
        if (endDate.compareTo(startDate) <= 0)
            throw new BusinessException("Coupon's start date should be later than end date!");
        couponMapper.insertCoupon(coupon);
    }

    @Override
    public void updateOrderWhenPickUp(Long id, Integer startOdometer) throws BusinessException {
        CustomerVehicle order = customerVehicleMapper.getOrderById(id);
        if (order == null)
            throw new BusinessException("Please enter the correct customer_vehicle_id.");
        customerVehicleMapper.updateOrderWhenPickUp(id, startOdometer, CustomerVehicle.PICKED_UP);
    }

    @Override
    @Transactional
    public void updateOrderWhenDropOff(Long id, Integer endOdometer) throws BusinessException {
        CustomerVehicle order = customerVehicleMapper.getOrderById(id);
        if (order == null)
            throw new BusinessException("Please enter the correct customer_vehicle_id.");
        vehicleMapper.updateLocation(order.getVehicleId(), order.getDropOffLocationId());
        customerVehicleMapper.updateOrderWhenDropOff(id, endOdometer, CustomerVehicle.DROPPED_OFF);
    }

    @Override
    public void assignDiscount(Long id, BigDecimal discount) throws BusinessException {
        Customer customer = customerMapper.getCustomerById(id);
        if (customer == null)
            throw new BusinessException("Cannot find this customer!");
        else if (!Customer.CORPORATE.equals(customer.getCustomerType()))
            throw new BusinessException("Must assign a discount to an existed corporate customer.");
        corporateMapper.assignDiscount(id, discount);
    }
}

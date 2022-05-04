package com.cly.backend.service.impl;

import com.cly.backend.entity.Coupon;
import com.cly.backend.entity.Customer;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.mapper.CorporateMapper;
import com.cly.backend.mapper.CouponMapper;
import com.cly.backend.mapper.CustomerMapper;
import com.cly.backend.mapper.CustomerVehicleMapper;
import com.cly.backend.service.AdminService;
import com.cly.backend.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Long adminRegister(String username, String password) {
        Customer admin = new Customer();
        admin.setUsername(username);
        String salt = ShiroUtils.randomSalt();
        admin.setSalt(salt);
        admin.setPassword(ShiroUtils.Sha256Hash(password, salt));
        admin.setCustomerType(Customer.ADMIN);
        admin.setEmail("");
        admin.setPhoneNumber("");
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
    public void updateOrderWhenPickUp(Long id, Integer startOdometer) {
        customerVehicleMapper.updateOrderWhenPickUp(id, startOdometer);
    }

    @Override
    public void updateOrderWhenDropOff(Long id, Integer endOdometer) {
        customerVehicleMapper.updateOrderWhenDropOff(id, endOdometer);
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

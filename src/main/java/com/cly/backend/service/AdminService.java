package com.cly.backend.service;

import com.cly.backend.entity.Coupon;
import com.cly.backend.form.AdminRegisterForm;

import java.math.BigDecimal;

public interface AdminService {
    void insertCoupon(Coupon coupon);
    Long adminRegister(AdminRegisterForm form);
    void updateOrderWhenPickUp(Long id, Integer startOdometer);
    void updateOrderWhenDropOff(Long id, Integer endOdometer);
    void assignDiscount(Long id, BigDecimal discount);
}

package com.cly.backend.service;

import com.cly.backend.entity.Coupon;

import java.math.BigDecimal;

public interface AdminService {
    void insertCoupon(Coupon coupon);
    Long adminRegister(String username, String password);
    void updateOrderWhenPickUp(Long id, Integer startOdometer);
    void updateOrderWhenDropOff(Long id, Integer endOdometer);
    void assignDiscount(Long id, BigDecimal discount);
}

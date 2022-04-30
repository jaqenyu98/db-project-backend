package com.cly.backend.service;

import com.cly.backend.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> listValidCoupons(Long individualId);
    List<Coupon> listUsedCoupons(Long individualId);
    List<Coupon> listInvalidCoupons(Long individualId);
}

package com.cly.backend.mapper;

import com.cly.backend.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {
    void insertCoupon(Coupon coupon);
    List<Coupon> listValidCoupons(Long individualId);
    List<Coupon> listUsedCoupons(Long individualId);
    List<Coupon> listInvalidCoupons(Long individualId);
    Coupon getCoupon(Long id);
    void setStatus(Long id, Integer status);
}

package com.cly.backend.service.impl;

import com.cly.backend.entity.Coupon;
import com.cly.backend.mapper.CouponMapper;
import com.cly.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Override
    @Transactional
    public List<Coupon> listValidCoupons(Long individualId) {
        List<Coupon> couponList = couponMapper.listValidCoupons(individualId);
        for(Coupon coupon: couponList){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime endDate = LocalDateTime.parse(coupon.getEndDate(), formatter);
            LocalDateTime currentDate = LocalDateTime.now();
            if (currentDate.compareTo(endDate) >= 0)
                couponMapper.setStatus(coupon.getId(), Coupon.INVALID);
        }
        return couponMapper.listValidCoupons(individualId);
    }

    @Override
    public List<Coupon> listUsedCoupons(Long individualId) {
        return couponMapper.listUsedCoupons(individualId);
    }

    @Override
    public List<Coupon> listInvalidCoupons(Long individualId) {
        return couponMapper.listInvalidCoupons(individualId);
    }
}

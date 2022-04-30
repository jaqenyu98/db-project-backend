package com.cly.backend.controller;

import com.cly.backend.entity.Coupon;
import com.cly.backend.service.CouponService;
import com.cly.backend.util.Result;
import com.cly.backend.util.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "API for individual coupon")
@RestController
@RequestMapping("coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @ApiOperation("List all the individual customer's valid coupons.")
    @GetMapping("valid")
    public Result<List<Coupon>> listValidCoupons(){
        Long individualId = ShiroUtils.getId();
        return Result.success(couponService.listValidCoupons(individualId));
    }

    @ApiOperation("List all the individual customer's used coupons.")
    @GetMapping("used")
    public Result<List<Coupon>> listUsedCoupons(){
        Long individualId = ShiroUtils.getId();
        return Result.success(couponService.listUsedCoupons(individualId));
    }

    @ApiOperation("List all the individual customer's invalid coupons.")
    @GetMapping("invalid")
    public Result<List<Coupon>> listInvalidCoupons(){
        Long individualId = ShiroUtils.getId();
        return Result.success(couponService.listInvalidCoupons(individualId));
    }
}

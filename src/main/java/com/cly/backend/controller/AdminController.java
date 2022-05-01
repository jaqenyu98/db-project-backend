package com.cly.backend.controller;

import com.cly.backend.entity.Coupon;
import com.cly.backend.entity.JwtUser;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.service.AdminService;
import com.cly.backend.util.JwtUtils;
import com.cly.backend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "API for administrator")
@RestController
@RequestMapping("admin")
@RequiresRoles("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("Register for administrators.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", dataTypeClass = String.class)
    })
    @PostMapping("register")
    public Result<Map<String, String>> adminRegister(@RequestParam String username, @RequestParam String password) {
        Long id = adminService.adminRegister(username, password);
        JwtUser jwtUser = new JwtUser(id, "admin");
        String jwtToken = jwtUtils.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("role", jwtUser.getRole());
        return Result.success(map);
    }

    @ApiOperation("Add coupon for individual customers.")
    @ApiImplicitParam(name = "json", dataTypeClass = Coupon.class)
    @PostMapping("coupons")
    public Result insertCoupon(@Validated @RequestBody Coupon coupon) {
        adminService.insertCoupon(coupon);
        return Result.success();
    }

    @ApiOperation("Assign discount for corporate customers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "corporate_id", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "discount", value = "0 < discount < 1", dataTypeClass = BigDecimal.class)
    })
    @PutMapping("discounts")
    public Result assignDiscount(@RequestParam Long id, @RequestParam BigDecimal discount) {
        if (discount.compareTo(new BigDecimal("0")) <= 0 || discount.compareTo(new BigDecimal("1")) >= 0)
            throw new BusinessException("Discount should be larger than 0 and less than 1.");
        adminService.assignDiscount(id, discount);
        return Result.success();
    }

    @ApiOperation("Fill in the start odometer when a vehicle is picked up.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "customer_vehicle_id", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "startOdometer", dataTypeClass = Integer.class)
    })
    @PutMapping("orders/pick_up")
    public Result updateOrderWhenPickUp(@RequestParam Long id, @RequestParam Integer startOdometer) {
        adminService.updateOrderWhenPickUp(id, startOdometer);
        return Result.success();
    }

    @ApiOperation("Fill in the end odometer when a vehicle is dropped off.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "customer_vehicle_id", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "endOdometer", dataTypeClass = Integer.class)
    })
    @PutMapping("orders/drop_off")
    public Result updateOrderWhenDropOff(@RequestParam Long id, @RequestParam Integer endOdometer) {
        adminService.updateOrderWhenDropOff(id, endOdometer);
        return Result.success();
    }
}

package com.cly.backend.controller;

import com.cly.backend.entity.CustomerVehicle;
import com.cly.backend.form.OrderForm;
import com.cly.backend.service.OrderService;
import com.cly.backend.util.Result;
import com.cly.backend.util.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API for order")
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("Reserve vehicles for customers")
    @ApiImplicitParam(name = "json", dataTypeClass = OrderForm.class)
    @PostMapping("reserve")
    public Result reserveOrder(@RequestBody OrderForm form) {
        Long customerId = ShiroUtils.getId();
        orderService.reserve(customerId, form);
        return Result.success();
    }

    @ApiOperation("List all the customer's reserved orders.")
    @GetMapping("reserved")
    public Result<List<CustomerVehicle>> listReservedOrders(){
        Long customerId = ShiroUtils.getId();
        return Result.success(orderService.listReservedOrders(customerId));
    }
    @ApiOperation("List all the customer's picked up orders.")
    @GetMapping("picked_up")
    public Result<List<CustomerVehicle>> listPickedUpOrders(){
        Long customerId = ShiroUtils.getId();
        return Result.success(orderService.listPickedUpOrders(customerId));
    }
    @ApiOperation("List all the customer's dropped off orders.")
    @GetMapping("dropped_off")
    public Result<List<CustomerVehicle>> listDroppedOffOrders(){
        Long customerId = ShiroUtils.getId();
        return Result.success(orderService.listDroppedOffOrders(customerId));
    }
    @ApiOperation("List all the customer's paid orders.")
    @GetMapping("paid")
    public Result<List<CustomerVehicle>> listPaidOrders(){
        Long customerId = ShiroUtils.getId();
        return Result.success(orderService.listPaidOrders(customerId));
    }

    

}

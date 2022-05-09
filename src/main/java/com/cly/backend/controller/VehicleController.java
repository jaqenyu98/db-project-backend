package com.cly.backend.controller;

import com.cly.backend.entity.Vehicle;
import com.cly.backend.form.AvailableVehiclesFilterForm;
import com.cly.backend.service.VehicleService;
import com.cly.backend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="API for vehicle")
@RestController
@RequestMapping("vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @ApiOperation("List all available vehicles according to customer's pick up location, pick up date and drop off date.")
    @ApiImplicitParam(name = "json", dataTypeClass = AvailableVehiclesFilterForm.class)
    @PostMapping
    public Result<List<Vehicle>> listVehiclesByFilter(@Validated @RequestBody AvailableVehiclesFilterForm form){
        return Result.success(vehicleService.listVehiclesByFilter(form.getLocationId(), form.getPickUpDate(), form.getDropOffDate()));
    }

    @ApiOperation("List all available vehicles sorted by daily rate.")
    @ApiImplicitParam(name = "json", dataTypeClass = AvailableVehiclesFilterForm.class)
    @PostMapping("sorted")
    public Result<List<Vehicle>> listSortedAvailableVehicles(@Validated @RequestBody AvailableVehiclesFilterForm form){
        return Result.success(vehicleService.listSortedVehiclesByFilter(form.getLocationId(), form.getPickUpDate(), form.getDropOffDate()));
    }
}

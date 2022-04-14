package com.cly.backend.controller;

import com.cly.backend.entity.Location;
import com.cly.backend.entity.Vehicle;
import com.cly.backend.service.LocationService;
import com.cly.backend.service.VehicleService;
import com.cly.backend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api(tags="API for vehicle")
@RestController
@RequestMapping("vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @ApiOperation("List all available vehicles according to customer's pick up location, pick up date and drop off date.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "location_id", value = "pick up location id", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pick_up_date", value = "Format: yyyy-MM-dd HH:mm:ss", dataTypeClass = String.class),
            @ApiImplicitParam(name = "drop_off_date", value = "Format: yyyy-MM-dd HH:mm:ss", dataTypeClass = String.class)
    })

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public Result<List<Vehicle>> listVehiclesByFilter(@RequestParam("location_id") Long locationId,
                                                      @RequestParam("pick_up_date") String pickUpDateStr,
                                                      @RequestParam("drop_off_date") String dropOffDateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pickUpDate = LocalDateTime.parse(pickUpDateStr, formatter);
        LocalDateTime dropOffDate = LocalDateTime.parse(dropOffDateStr, formatter);
        return Result.success(vehicleService.listVehiclesByFilter(locationId, pickUpDate, dropOffDate));
    }

}

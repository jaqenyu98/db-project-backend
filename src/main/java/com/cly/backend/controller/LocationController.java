package com.cly.backend.controller;

import com.cly.backend.entity.Location;
import com.cly.backend.service.LocationService;
import com.cly.backend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "API for Location")
@RestController
@RequestMapping("locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @ApiOperation("List All office locations.")
    @GetMapping
    public Result<List<Location>> listLocations() {
        return Result.success(locationService.listLocations());
    }
}

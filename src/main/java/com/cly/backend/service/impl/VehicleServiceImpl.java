package com.cly.backend.service.impl;

import com.cly.backend.entity.Location;
import com.cly.backend.entity.Vehicle;
import com.cly.backend.mapper.VehicleMapper;
import com.cly.backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> listVehiclesByFilter(Long locationId, LocalDateTime pickUpDate, LocalDateTime dropOffDate) {
        return vehicleMapper.listVehiclesByFilter(locationId, pickUpDate, dropOffDate);
    }

}

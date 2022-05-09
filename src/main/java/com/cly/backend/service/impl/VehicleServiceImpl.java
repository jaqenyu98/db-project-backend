package com.cly.backend.service.impl;

import com.cly.backend.entity.Vehicle;
import com.cly.backend.mapper.VehicleMapper;
import com.cly.backend.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> listVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate) {
        return vehicleMapper.listVehiclesByFilter(locationId, pickUpDate, dropOffDate);
    }
    @Override
    public List<Vehicle> listSortedVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate) {
        return vehicleMapper.listSortedVehiclesByFilter(locationId, pickUpDate, dropOffDate);
    }

}

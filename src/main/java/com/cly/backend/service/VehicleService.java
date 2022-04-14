package com.cly.backend.service;

import com.cly.backend.entity.Location;
import com.cly.backend.entity.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
    List<Vehicle> listVehiclesByFilter(Long locationId, LocalDateTime pickUpDate, LocalDateTime dropOffDate);
}

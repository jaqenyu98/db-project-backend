package com.cly.backend.service;

import com.cly.backend.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> listVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate);
    List<Vehicle> listSortedVehiclesByFilter(Long locationId, String pickUpDate, String dropOffDate);
}

package com.cly.backend.entity;

import lombok.Data;

@Data
public class Vehicle {
    private Long id;
    private String vin;
    private String make;
    private String model;
    private String year;
    private String licensePlate;
    private Long locationId;
    private Long vehicleCategoryId;
    private VehicleCategory vehicleCategory;
    private Location location;
}

package com.cly.backend.entity;

import lombok.Data;
import java.time.Year;

@Data
public class Vehicle {
    private Long id;
    private String vin;
    private String make;
    private String model;
    private Year year;
    private String licensePlate;
    private Long locationId;
    private Long vehicleCategoryId;
}

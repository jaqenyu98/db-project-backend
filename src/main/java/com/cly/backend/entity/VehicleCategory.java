package com.cly.backend.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleCategory {
    private Long id;
    private String name;
    private BigDecimal dailyRate;
    private BigDecimal overMileageRate;
}

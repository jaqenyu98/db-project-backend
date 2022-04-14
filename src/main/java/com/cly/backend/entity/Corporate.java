package com.cly.backend.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Corporate {
    private Long id;
    private String name;
    private String registrationNumber;
    private String employeeId;
    private BigDecimal discount;
}

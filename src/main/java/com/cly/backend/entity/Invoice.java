package com.cly.backend.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Invoice {
    private Long id;
    private String createDate;
    private BigDecimal amount;
    private Long customerVehicleId;
}

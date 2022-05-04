package com.cly.backend.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Invoice {
    private Long id;
    private String createDate;
    private BigDecimal amount;
    private Long customerVehicleId;
    private List<Payment> payments;
}

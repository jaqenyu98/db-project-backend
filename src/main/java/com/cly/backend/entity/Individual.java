package com.cly.backend.entity;

import lombok.Data;

@Data
public class Individual {
    private Long id;
    private String firstName;
    private String lastName;
    private String insuranceCompanyName;
    private String insurancePolicyNumber;
    private String driverLicenseNumber;
}

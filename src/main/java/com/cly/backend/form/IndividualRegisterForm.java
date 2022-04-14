package com.cly.backend.form;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class IndividualRegisterForm {
    private String username;
    private String password;
    @Email(message = "Please enter the correct email address.")
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String insuranceCompanyName;
    private String insurancePolicyNumber;
    private String driverLicenseNumber;
}

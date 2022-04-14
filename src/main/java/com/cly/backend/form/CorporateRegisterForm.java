package com.cly.backend.form;

import lombok.Data;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

@Data
public class CorporateRegisterForm{
    private String username;
    private String password;
    @Email(message = "Please enter the correct email address.")
    private String email;
    private String phoneNumber;
    private String name;
    private String registrationNumber;
    private String employeeId;
}

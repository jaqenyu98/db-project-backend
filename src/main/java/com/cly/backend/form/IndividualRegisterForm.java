package com.cly.backend.form;

import com.cly.backend.util.Xss;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class IndividualRegisterForm {
    @Xss(message = "Username cannot contain any html tag!")
    @NotBlank(message = "Username cannot be blank!")
    private String username;
    @Xss(message = "Password cannot contain any html tag!")
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @Email(message = "Please enter the correct email address.")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Please enter 10 digits. For example: 0000000000.")
    private String phoneNumber;
    @NotBlank(message = "First name cannot be blank!")
    @Xss(message = "First name cannot contain any html tag!")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank!")
    @Xss(message = "Last name cannot contain any html tag!")
    private String lastName;
    @NotBlank(message = "Insurance company name cannot be blank!")
    @Xss(message = "Insurance company name cannot contain any html tag!")
    private String insuranceCompanyName;
    @NotBlank(message = "Insurance Policy number cannot be blank!")
    @Xss(message = "Insurance Policy number cannot contain any html tag!")
    private String insurancePolicyNumber;
    @NotBlank(message = "Driver license number cannot be blank!")
    @Xss(message = "Driver license number cannot contain any html tag!")
    private String driverLicenseNumber;
}

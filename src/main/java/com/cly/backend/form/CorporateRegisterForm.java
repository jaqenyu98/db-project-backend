package com.cly.backend.form;

import com.cly.backend.util.Xss;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CorporateRegisterForm{
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
    @NotBlank(message = "Corporate name cannot be blank!")
    @Xss(message = "Corporate name cannot contain any html tag!")
    private String name;
    @NotBlank(message = "Registration number cannot be blank!")
    @Xss(message = "Registration number cannot contain any html tag!")
    private String registrationNumber;
    @NotBlank(message = "Employee id cannot be blank!")
    @Xss(message = "Employee id cannot contain any html tag!")
    private String employeeId;
}

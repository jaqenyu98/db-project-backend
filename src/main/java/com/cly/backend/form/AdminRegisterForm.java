package com.cly.backend.form;

import com.cly.backend.util.Xss;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AdminRegisterForm {
    @NotBlank(message = "Username cannot be blank!")
    @Xss(message = "Username cannot contain any html tag!")
    private String username;
    @Xss(message = "Password cannot contain any html tag!")
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @Email(message = "Please enter the correct email address.")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Please enter 10 digits. For example: 0000000000.")
    private String phoneNumber;
}

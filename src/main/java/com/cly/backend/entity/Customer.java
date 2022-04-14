package com.cly.backend.entity;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String username;
    private String password;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String email;
    private String phoneNumber;
    private String customerType;
    private String salt;
}

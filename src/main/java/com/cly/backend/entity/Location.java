package com.cly.backend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Location {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipcode;
}

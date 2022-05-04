package com.cly.backend.entity;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CustomerVehicle {
    private Long id;
    private Long customerId;
    private Long vehicleId;
    private Long pickUpLocationId;
    private Long dropOffLocationId;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"
            , message = "The format of startDate is incorrect. The correct format is: yyyy-MM-dd")
    private String pickUpDate;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"
            , message = "The format of startDate is incorrect. The correct format is: yyyy-MM-dd")
    private String dropOffDate;
    private Integer startOdometer;
    private Integer endOdometer;
    private Integer dailyOdometerLimit;
    private Long couponId;
    //'0 for reserved, 1 for picked up, 2 for returned, 3 for paid.'
    private Integer status;
    private Vehicle vehicle;
    private Location pickUpLocation;
    private Location dropOffLocation;

    public static final int RESERVED = 0;
    public static final int PICKED_UP = 1;
    public static final int DROPPED_OFF = 2;
    public static final int PAID = 3;
}

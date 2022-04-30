package com.cly.backend.form;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class OrderForm {
    private Long vehicleId;
    private Long pickUpLocationId;
    private Long dropOffLocationId;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$"
            , message = "The format of pickUpDate is incorrect. The correct format is: yyyy-MM-dd HH:mm:ss")
    private String pickUpDate;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$"
            , message = "The format of pickUpDate is incorrect. The correct format is: yyyy-MM-dd HH:mm:ss")
    private String dropOffDate;
    private Integer dailyOdometerLimit;
    private Long couponId;
}

package com.cly.backend.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class Coupon {
    private Long id;
    @Range(min = 0, max = 1, message = "Discount should range from 0 to 1. 0.2 means 20% Off.")
    private BigDecimal discount;
    private String description;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"
            , message = "The format of startDate is incorrect. The correct format is: yyyy-MM-dd")
    private String startDate;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"
            , message = "The format of endDate is incorrect. The correct format is: yyyy-MM-dd")
    private String endDate;
    //'The status of this coupon, 0 for valid, 1 for used, 2 for invalid.'
    private Integer status;
    private Long individualId;

    public static final int VALID = 0;
    public static final int USED = 1;
    public static final int INVALID = 2;
}

package com.cly.backend.entity;

import com.cly.backend.util.Xss;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class Payment {
    @Null(message = "Payment id should be null. It's auto-increment.")
    private Long id;
    @NotBlank(message = "Payment method cannot be blank!")
    @Xss(message = "Payment method cannot contain any html tag!")
    private String method;
    @Pattern(regexp = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$"
            , message = "The format of paymentDate is incorrect. The correct format is: yyyy-MM-dd HH:mm:ss. Just input the current datetime.")
    private String paymentDate;
    @NotBlank(message = "Card number cannot be blank!")
    @Xss(message = "Card number cannot contain any html tag!")
    private String cardNumber;
    private BigDecimal amount;
    private Long invoiceId;

    public static final String METHOD_CREDIT_CARD = "C";
    public static final String METHOD_DEBIT_CARD = "D";
    public static final String METHOD_GIFT_CARD = "G";
}

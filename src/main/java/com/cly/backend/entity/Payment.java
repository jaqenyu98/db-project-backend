package com.cly.backend.entity;

import lombok.Data;

@Data
public class Payment {
    private Long id;
    private String method;
    private String paymentDate;
    private String cardNumber;
    private Long invoiceId;

    public static final String METHOD_CREDIT_CARD = "C";
    public static final String METHOD_DEBIT_CARD = "D";
    public static final String METHOD_GIFT_CARD = "G";
}

package com.cly.backend.mapper;

import com.cly.backend.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    List<Payment> listPaymentsByInvoiceId(Long invoiceId);
    void insertPayment(Payment payment);
}

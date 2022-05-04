package com.cly.backend.mapper;

import com.cly.backend.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvoiceMapper {
    Invoice getInvoiceByOrderId(Long customerVehicleId);
}

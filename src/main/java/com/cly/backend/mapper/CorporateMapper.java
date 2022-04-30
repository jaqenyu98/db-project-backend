package com.cly.backend.mapper;

import com.cly.backend.entity.Corporate;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface CorporateMapper {
    void corporateRegister(Corporate corporate);
    void assignDiscount(Long id, BigDecimal discount);
    Integer checkRegistrationNumber(String registrationNumber);
}

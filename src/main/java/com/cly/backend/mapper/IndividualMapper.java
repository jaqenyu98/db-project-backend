package com.cly.backend.mapper;

import com.cly.backend.entity.Individual;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndividualMapper {
    void individualRegister(Individual individual);
    Integer checkDriverLicenseNumber(String driverLicenseNumber);
}

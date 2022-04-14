package com.cly.backend.mapper;

import com.cly.backend.entity.Corporate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CorporateMapper {
    void corporateRegister(Corporate corporate);
}

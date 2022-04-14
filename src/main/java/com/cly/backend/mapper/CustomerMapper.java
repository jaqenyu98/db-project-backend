package com.cly.backend.mapper;

import com.cly.backend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {

    Customer getCustomerByUsername(String username);
    void customerRegister(Customer customer);
}

package com.cly.backend.mapper;

import com.cly.backend.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
    Customer getCustomerByUsername(String username);
    void customerRegister(Customer customer);
    Customer getCustomerById(Long id);
    Integer checkUsername(String username);
    Integer checkEmail(String email);
    Integer checkPhoneNumber(String phoneNumber);
    void updateAddress(Long id, String street, String city, String state, String zipcode);
}

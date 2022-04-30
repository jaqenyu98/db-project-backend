package com.cly.backend.service;

import com.cly.backend.entity.Customer;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;

public interface CustomerService {

    Customer getCustomerByUsername(String username);
    Long individualRegister(IndividualRegisterForm form);
    Long corporateRegister(CorporateRegisterForm form);
    void updateAddress(Long id, String street, String city, String state, String zipcode);
}

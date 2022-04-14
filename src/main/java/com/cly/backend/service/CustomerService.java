package com.cly.backend.service;

import com.cly.backend.entity.Customer;
import com.cly.backend.entity.Individual;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;
import com.cly.backend.form.LoginForm;

public interface CustomerService {

    Customer getCustomerByUsername(String username);
    Long individualRegister(IndividualRegisterForm form);
    Long corporateRegister(CorporateRegisterForm form);
}

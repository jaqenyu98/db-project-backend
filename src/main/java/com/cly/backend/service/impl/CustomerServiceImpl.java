package com.cly.backend.service.impl;


import com.cly.backend.entity.Corporate;
import com.cly.backend.entity.Customer;
import com.cly.backend.entity.Individual;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;
import com.cly.backend.mapper.CorporateMapper;
import com.cly.backend.mapper.CustomerMapper;
import com.cly.backend.mapper.IndividualMapper;
import com.cly.backend.service.CustomerService;
import com.cly.backend.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private IndividualMapper individualRegister;
    @Autowired
    private CorporateMapper corporateMapper;

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerMapper.getCustomerByUsername(username);
    }

    @Override
    @Transactional
    public Long individualRegister(IndividualRegisterForm form) {
        //to do: validate unique username
        Customer customer = new Customer();
        customer.setUsername(form.getUsername());
        String salt = ShiroUtil.randomSalt();
        customer.setSalt(salt);
        customer.setPassword(ShiroUtil.Sha256Hash(form.getPassword(), salt));
        customer.setEmail(form.getEmail());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setCustomerType("I");
        customerMapper.customerRegister(customer);
        Individual individual = new Individual();
        individual.setId(customer.getId());
        individual.setFirstName(form.getFirstName());
        individual.setLastName(form.getLastName());
        individual.setDriverLicenseNumber(form.getDriverLicenseNumber());
        individual.setInsuranceCompanyName(form.getInsuranceCompanyName());
        individual.setInsurancePolicyNumber(form.getInsurancePolicyNumber());
        individualRegister.individualRegister(individual);
        return customer.getId();
    }

    @Override
    @Transactional
    public Long corporateRegister(CorporateRegisterForm form) {
        //to do: validate unique username
        Customer customer = new Customer();
        customer.setUsername(form.getUsername());
        String salt = ShiroUtil.randomSalt();
        customer.setSalt(salt);
        customer.setPassword(ShiroUtil.Sha256Hash(form.getPassword(), salt));
        customer.setEmail(form.getEmail());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setCustomerType("C");
        customerMapper.customerRegister(customer);
        Corporate corporate = new Corporate();
        corporate.setId(customer.getId());
        corporate.setName(form.getName());
        corporate.setRegistrationNumber(form.getRegistrationNumber());
        corporate.setEmployeeId(form.getEmployeeId());
        corporateMapper.corporateRegister(corporate);
        return customer.getId();
    }

}

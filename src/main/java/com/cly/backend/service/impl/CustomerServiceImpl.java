package com.cly.backend.service.impl;


import com.cly.backend.entity.Corporate;
import com.cly.backend.entity.Customer;
import com.cly.backend.entity.Individual;
import com.cly.backend.exception.BusinessException;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;
import com.cly.backend.mapper.CorporateMapper;
import com.cly.backend.mapper.CustomerMapper;
import com.cly.backend.mapper.IndividualMapper;
import com.cly.backend.service.CustomerService;
import com.cly.backend.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private IndividualMapper individualMapper;
    @Autowired
    private CorporateMapper corporateMapper;

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerMapper.getCustomerByUsername(username);
    }

    @Override
    @Transactional
    public Long individualRegister(IndividualRegisterForm form) throws BusinessException{
        checkIndividualRegister(form);
        Customer customer = new Customer();
        customer.setUsername(form.getUsername());
        String salt = ShiroUtils.randomSalt();
        customer.setSalt(salt);
        customer.setPassword(ShiroUtils.Sha256Hash(form.getPassword(), salt));
        customer.setEmail(form.getEmail());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setCustomerType(Customer.INDIVIDUAL);
        customerMapper.customerRegister(customer);

        Individual individual = new Individual();
        individual.setId(customer.getId());
        individual.setFirstName(form.getFirstName());
        individual.setLastName(form.getLastName());
        individual.setDriverLicenseNumber(form.getDriverLicenseNumber());
        individual.setInsuranceCompanyName(form.getInsuranceCompanyName());
        individual.setInsurancePolicyNumber(form.getInsurancePolicyNumber());
        individualMapper.individualRegister(individual);

        return customer.getId();
    }

    @Override
    @Transactional
    public Long corporateRegister(CorporateRegisterForm form) throws BusinessException{
        checkCorporateRegister(form);
        Customer customer = new Customer();
        customer.setUsername(form.getUsername());
        String salt = ShiroUtils.randomSalt();
        customer.setSalt(salt);
        customer.setPassword(ShiroUtils.Sha256Hash(form.getPassword(), salt));
        customer.setEmail(form.getEmail());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setCustomerType(Customer.CORPORATE);
        customerMapper.customerRegister(customer);

        Corporate corporate = new Corporate();
        corporate.setId(customer.getId());
        corporate.setName(form.getName());
        corporate.setRegistrationNumber(form.getRegistrationNumber());
        corporate.setEmployeeId(form.getEmployeeId());
        corporateMapper.corporateRegister(corporate);
        return customer.getId();
    }

    @Override
    public void updateAddress(Long id, String street, String city, String state, String zipcode) {
        customerMapper.updateAddress(id, street, city, state, zipcode);
    }

    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) throws BusinessException{
        Customer customer = customerMapper.getCustomerById(id);
        String oldEncryptedPassword = ShiroUtils.Sha256Hash(oldPassword, customer.getSalt());
        if (!oldEncryptedPassword.equals(customer.getPassword()))
            throw new BusinessException("Old password is wrong!");
        String newEncryptedPassword = ShiroUtils.Sha256Hash(newPassword, customer.getSalt());
        if (oldEncryptedPassword.equals(newEncryptedPassword))
            throw new BusinessException("The new password cannot be the same as the old password!");
        customerMapper.updatePassword(id, newEncryptedPassword);
    }

    private void checkIndividualRegister(IndividualRegisterForm form) throws BusinessException{
        if (customerMapper.checkUsername(form.getUsername()) >= 1)
            throw new BusinessException("Duplicated username!");
        if (customerMapper.checkEmail(form.getEmail()) >= 1)
            throw new BusinessException("Duplicated email!");
        if (customerMapper.checkPhoneNumber(form.getPhoneNumber()) >= 1)
            throw new BusinessException("Duplicated phone number!");
        if (individualMapper.checkDriverLicenseNumber(form.getDriverLicenseNumber()) >= 1)
            throw new BusinessException("Duplicated driver license number!");
    }
    private void checkCorporateRegister(CorporateRegisterForm form) throws BusinessException{
        if (customerMapper.checkUsername(form.getUsername()) >= 1)
            throw new BusinessException("Duplicated username!");
        if (customerMapper.checkEmail(form.getEmail()) >= 1)
            throw new BusinessException("Duplicated email!");
        if (customerMapper.checkPhoneNumber(form.getPhoneNumber()) >= 1)
            throw new BusinessException("Duplicated phone number!");
        if (corporateMapper.checkRegistrationNumber(form.getRegistrationNumber()) >= 1)
            throw new BusinessException("Duplicated registration number!");
    }

}

package com.cly.backend.realm;

import com.cly.backend.entity.Customer;
import com.cly.backend.entity.JwtUser;
import com.cly.backend.service.CustomerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class LoginRealm extends AuthenticatingRealm {
    private static final int HASH_ITERATIONS = 20;

    //you have to use @Lazy to ensure @Transactional used on customerService!
    @Lazy
    @Autowired
    private CustomerService customerService;

    public LoginRealm() {
        super();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        hashedCredentialsMatcher.setHashIterations(HASH_ITERATIONS);
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Customer customer = customerService.getCustomerByUsername(username);
        if (customer == null)
            throw new AuthenticationException("Cannot find this user");
        String password = customer.getPassword();
        String salt = customer.getSalt();

        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(customer.getId());
        String customerType = customer.getCustomerType();
        switch (customerType) {
            case Customer.INDIVIDUAL:
                jwtUser.setRole("individual");
                break;
            case Customer.CORPORATE:
                jwtUser.setRole("corporate");
                break;
            case Customer.ADMIN:
                jwtUser.setRole("admin");
                break;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(jwtUser, password, ByteSource.Util.bytes(salt), getName());
        return info;
    }

}

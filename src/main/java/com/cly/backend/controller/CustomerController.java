package com.cly.backend.controller;

import com.cly.backend.entity.JwtUser;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;
import com.cly.backend.form.LoginForm;
import com.cly.backend.service.CustomerService;
import com.cly.backend.util.JwtUtil;
import com.cly.backend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "API for customer")
@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtUtil jwtUtil;

    @ApiOperation("Login for customers.")
    @ApiImplicitParam(name = "LoginForm", value = "json", dataTypeClass = LoginForm.class)
    @PostMapping("login")
    public Result<Map<String, String>> customerLogin(@RequestBody LoginForm form) {
        UsernamePasswordToken token = new UsernamePasswordToken(form.getUsername(), form.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        JwtUser jwtUser = (JwtUser) subject.getPrincipal();
        String jwtToken = jwtUtil.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        return Result.success(map);
    }

    @ApiOperation("Register for individual customers.")
    @ApiImplicitParam(name = "IndividualRegisterForm", value = "json", dataTypeClass = IndividualRegisterForm.class)
    @PostMapping("register/individual")
    public Result<Map<String, String>> individualRegister(@Validated @RequestBody IndividualRegisterForm form) {
        Long id = customerService.individualRegister(form);
        JwtUser jwtUser = new JwtUser(id, "individual");
        String jwtToken = jwtUtil.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        return Result.success(map);
    }

    @ApiOperation("Register for corporate customers.")
    @ApiImplicitParam(name = "CorporateRegisterForm", value = "json", dataTypeClass = CorporateRegisterForm.class)
    @PostMapping("register/corporate")
    public Result<Map<String, String>> corporateRegister(@Validated @RequestBody CorporateRegisterForm form) {
        Long id = customerService.corporateRegister(form);
        JwtUser jwtUser = new JwtUser(id, "individual");
        String jwtToken = jwtUtil.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        return Result.success(map);
    }

}

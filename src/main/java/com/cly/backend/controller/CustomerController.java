package com.cly.backend.controller;

import com.cly.backend.entity.JwtUser;
import com.cly.backend.form.CorporateRegisterForm;
import com.cly.backend.form.IndividualRegisterForm;
import com.cly.backend.form.LoginForm;
import com.cly.backend.service.CustomerService;
import com.cly.backend.util.JwtUtils;
import com.cly.backend.util.Result;
import com.cly.backend.util.ShiroUtils;
import com.cly.backend.util.Xss;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "API for customer")
@RestController
@RequestMapping("customers")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("Login for customers.")
    @ApiImplicitParam(name = "json", dataTypeClass = LoginForm.class)
    @PostMapping("login")
    public Result<Map<String, String>> customerLogin(@Validated @RequestBody LoginForm form) {
        UsernamePasswordToken token = new UsernamePasswordToken(form.getUsername(), form.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        JwtUser jwtUser = (JwtUser) subject.getPrincipal();
        String jwtToken = jwtUtils.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("role", jwtUser.getRole());
        return Result.success(map);
    }

    @ApiOperation("Register for individual customers.")
    @ApiImplicitParam(name = "json", dataTypeClass = IndividualRegisterForm.class)
    @PostMapping("register/individual")
    public Result<Map<String, String>> individualRegister(@Validated @RequestBody IndividualRegisterForm form) {
        Long id = customerService.individualRegister(form);
        JwtUser jwtUser = new JwtUser(id, "individual");
        String jwtToken = jwtUtils.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("role", jwtUser.getRole());
        return Result.success(map);
    }

    @ApiOperation("Register for corporate customers.")
    @ApiImplicitParam(name = "json", dataTypeClass = CorporateRegisterForm.class)
    @PostMapping("register/corporate")
    public Result<Map<String, String>> corporateRegister(@Validated @RequestBody CorporateRegisterForm form) {
        Long id = customerService.corporateRegister(form);
        JwtUser jwtUser = new JwtUser(id, "individual");
        String jwtToken = jwtUtils.createToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("role", jwtUser.getRole());
        return Result.success(map);
    }

    @ApiOperation("Update address information for customers.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "street", dataTypeClass = String.class),
            @ApiImplicitParam(name = "city", dataTypeClass = String.class),
            @ApiImplicitParam(name = "state", dataTypeClass = String.class),
            @ApiImplicitParam(name = "zipcode", dataTypeClass = String.class)
    })
    @PutMapping("address")
    public Result updateAddress(@Xss(message = "Street cannot contain any html tag!") @RequestParam String street,
                                @Xss(message = "City cannot contain any html tag!") @RequestParam String city,
                                @Xss(message = "State cannot contain any html tag!") @RequestParam String state,
                                @Xss(message = "Zipcode cannot contain any html tag!") @RequestParam String zipcode) {
        Long id = ShiroUtils.getId();
        customerService.updateAddress(id, street, city, state, zipcode);
        return Result.success();
    }

    @ApiOperation("Change password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", dataTypeClass = String.class),
            @ApiImplicitParam(name = "newPassword", dataTypeClass = String.class)
    })
    @PutMapping("password")
    public Result changePassword(@Xss(message = "Old password cannot contain any html tag!")
                                 @NotBlank(message = "Old password cannot be blank!")
                                 @RequestParam String oldPassword,
                                 @Xss(message = "New password cannot contain any html tag!")
                                 @NotBlank(message = "New password cannot be blank!")
                                 @RequestParam String newPassword) {
        Long id = ShiroUtils.getId();
        customerService.updatePassword(id, oldPassword, newPassword);
        return Result.success();
    }

}

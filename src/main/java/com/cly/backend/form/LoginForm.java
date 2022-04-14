package com.cly.backend.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
}

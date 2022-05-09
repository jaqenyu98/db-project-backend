package com.cly.backend.form;

import com.cly.backend.util.Xss;
import lombok.Data;

@Data
public class LoginForm {
    @Xss
    private String username;
    @Xss
    private String password;
}

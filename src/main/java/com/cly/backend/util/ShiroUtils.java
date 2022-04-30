package com.cly.backend.util;

import com.cly.backend.entity.JwtUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class ShiroUtils {
    private static final int HASH_ITERATIONS = 20;
    public static String randomSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes().toHex();
        return hex;
    }
    public static String Sha256Hash(String password, String salt){
        return new Sha256Hash(password, salt, HASH_ITERATIONS).toHex();
    }
    public static Long getId() {
        JwtUser jwtUser = (JwtUser) SecurityUtils.getSubject().getPrincipal();
        return jwtUser.getId();
    }

}

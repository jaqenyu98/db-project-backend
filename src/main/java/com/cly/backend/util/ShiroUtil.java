package com.cly.backend.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class ShiroUtil {
    private static final int HASH_ITERATIONS = 20;
    public static String randomSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes().toHex();
        return hex;
    }
    public static String Sha256Hash(String password, String salt){
        return new Sha256Hash(password, salt, HASH_ITERATIONS).toHex();
    }
}

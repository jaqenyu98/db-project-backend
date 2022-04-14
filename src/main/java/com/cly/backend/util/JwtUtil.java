package com.cly.backend.util;

import com.cly.backend.entity.JwtUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.header}")
    private String header;

    // token secret
    @Value("${jwt.secret}")
    private String secret;

    // expire time, default 30 minutes
    @Value("${jwt.expireTime}")
    private Long expireTime;

    //token：subject = customerId, role
    public String createToken(JwtUser jwtUser) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+expireTime*60*1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", jwtUser.getId().toString());
        claims.put("role", jwtUser.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //It could check whether the token is not only tampered with but also expired.
    //So we don't need a function to check expiration.
    //don't forget: if(claim==null)
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException e){
            log.info("Cannot get claim by token", e);
            return null;
        }
    }
}

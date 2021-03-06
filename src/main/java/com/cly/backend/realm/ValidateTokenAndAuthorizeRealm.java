package com.cly.backend.realm;

import com.cly.backend.entity.JwtUser;
import com.cly.backend.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateTokenAndAuthorizeRealm extends AuthorizingRealm {
    @Autowired
    private JwtUtils jwtUtils;

    public ValidateTokenAndAuthorizeRealm() {
        super();
        CredentialsMatcher credentialsMatcher = (token, info) -> true;
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof BearerToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        JwtUser jwtUser = (JwtUser)principals.getPrimaryPrincipal();
        // Complicated Authorization: store role and permission into database.
        // Long id = jwtUser.getId();
        // Set<String> permsSet = shiroService.getUserPermissions(userId);
        info.addRole(jwtUser.getRole());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException, JwtException {
        BearerToken bearerToken = (BearerToken) token;
        String jwtToken = bearerToken.getToken();
        Claims claims = jwtUtils.getClaimByToken(jwtToken);
        // judge whether token is tampered with or expired.
        if (claims == null)
            throw new JwtException("Invalid token.");
        JwtUser jwtUser = new JwtUser(Long.parseLong(claims.getSubject()), claims.get("role").toString());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(jwtUser, claims.getSubject(), getName());
        return info;
    }
}

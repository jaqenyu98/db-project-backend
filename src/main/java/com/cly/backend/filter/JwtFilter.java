package com.cly.backend.filter;

import com.cly.backend.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends BearerHttpAuthenticationFilter {

    //Send message to front end when failing to authenticate.
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=UTF-8");
        try {
            Result result = Result.unauthorized();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(result);
            httpResponse.getWriter().append(jsonResult);
        } catch (IOException e) {
            log.error("sendChallenge error");
        }
        return false;
    }
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

/**-----------------------------Copy source code of BearerHttpAuthenticationFilter-----------------------------**/
    private static final String BEARER = "Bearer";

    public JwtFilter() {
        setAuthcScheme(BEARER);
        setAuthzScheme(BEARER);
    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String authorizationHeader = getAuthzHeader(request);
        if (authorizationHeader == null || authorizationHeader.length() == 0) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return createBearerToken("", request);
        }

        log.debug("Attempting to execute login with auth header");

        String[] prinCred = getPrincipalsAndCredentials(authorizationHeader, request);
        if (prinCred == null || prinCred.length < 1) {
            // Create an authentication token with an empty password,
            // since one hasn't been provided in the request.
            return createBearerToken("", request);
        }

        String token = prinCred[0] != null ? prinCred[0] : "";
        return createBearerToken(token, request);
    }
    @Override
    protected String[] getPrincipalsAndCredentials(String scheme, String token) {
        return new String[] {token};
    }

    protected AuthenticationToken createBearerToken(String token, ServletRequest request) {
        return new BearerToken(token, request.getRemoteHost());
    }
}

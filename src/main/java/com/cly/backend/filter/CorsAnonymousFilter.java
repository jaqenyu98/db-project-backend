package com.cly.backend.filter;

import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsAnonymousFilter extends AnonymousFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization");
        httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}

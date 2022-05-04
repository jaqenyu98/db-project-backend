package com.cly.backend.exception;

import com.cly.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return Result.unauthorized("Wrong password!");
    }
    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return Result.unauthorized(e.getMessage());
    }
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e){
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.validateFailed(message);
    }
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error(e.getMessage(), e);
        return Result.validateFailed(e.getMessage());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}' doesn't support '{}' request", requestURI, e.getMethod());
        return Result.failed(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}' has a unknown exception.", requestURI, e);
        return Result.failed(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}' has a system exception.", requestURI, e);
        return Result.failed(e.getMessage());
    }
}

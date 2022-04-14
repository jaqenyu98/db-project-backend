package com.cly.backend.exception;

import com.cly.backend.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return Result.unauthorized(e.getMessage());
    }
    @ExceptionHandler(AuthorizationException.class)
    public Result authorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return Result.unauthorized(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeException(RuntimeException e){
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage());
    }
    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e){
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.validateFailed(message);
    }
}

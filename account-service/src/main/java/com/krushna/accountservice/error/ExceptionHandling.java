package com.krushna.accountservice.error;

import com.krushna.accountservice.entity.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDescription> handleException(Exception ex, WebRequest request) {
        // Create a custom ErrorResponse object and return it with the desired HTTP status code.
        log.warn(ex.getMessage());
        ErrorMessageDescription message=new ErrorMessageDescription(HttpStatus.INTERNAL_SERVER_ERROR.value(),new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RoleNotPresentException.class)
    public ResponseEntity<ErrorMessageDescription> handleRoleNotPresentException(Exception ex, WebRequest request) {
        // Create a custom ErrorResponse object and return it with the desired HTTP status code.
        log.warn(ex.getMessage());
        ErrorMessageDescription message=new ErrorMessageDescription(HttpStatus.NOT_ACCEPTABLE.value(),new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }
}


package com.krushna.transactionservice.error;

//import com.javalearning.springbootdemo.entity.ErrorMessage;

import com.krushna.transactionservice.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FundManagerNotFoundException.class)
    public ResponseEntity<ErrorMessage> fundManagerNotFoundException(FundManagerNotFoundException FMNFException, WebRequest request){

        System.out.println("Generating the Error Response");
        ErrorMessage msg=new ErrorMessage(HttpStatus.NOT_FOUND,FMNFException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }
}

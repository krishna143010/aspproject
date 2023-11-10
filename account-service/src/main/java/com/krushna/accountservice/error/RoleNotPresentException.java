package com.krushna.accountservice.error;

public class RoleNotPresentException extends RuntimeException {
    public RoleNotPresentException(String message){
        super(message);
    }
}

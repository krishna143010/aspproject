package com.krushna.accountservice.error;

public class UserNameOrEmailConflictException extends RuntimeException {
    public UserNameOrEmailConflictException(String message){
        super(message);
    }
}

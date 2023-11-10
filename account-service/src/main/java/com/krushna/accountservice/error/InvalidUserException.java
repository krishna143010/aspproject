package com.krushna.accountservice.error;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message){
        super(message);
    }
}

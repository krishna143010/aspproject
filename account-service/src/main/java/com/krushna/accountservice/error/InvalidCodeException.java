package com.krushna.accountservice.error;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException(String message){
        super(message);
    }
}

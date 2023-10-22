package com.krushna.accountservice.error;

public class FundManagerNotFoundException extends Exception{
    public FundManagerNotFoundException() {
        super();
    }

    public FundManagerNotFoundException(String message) {
        super(message);
    }

    public FundManagerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FundManagerNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FundManagerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

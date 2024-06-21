package com.retail.store.exception;

public class CustomerException extends RuntimeException {
    private String message;

    public CustomerException(String message) {
        super(message);
        this.message = message;
    }
}

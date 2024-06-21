package com.retail.store.exception;

public class BillException extends RuntimeException {

    private String message;

    public BillException(String message) {
        super(message);
        this.message = message;
    }
}

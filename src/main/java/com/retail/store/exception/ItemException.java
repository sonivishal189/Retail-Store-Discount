package com.retail.store.exception;

public class ItemException extends RuntimeException {
    private String message;

    public ItemException(String message) {
        super(message);
        this.message = message;
    }
}

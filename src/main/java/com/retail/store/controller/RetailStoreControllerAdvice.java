package com.retail.store.controller;

import com.retail.store.exception.BillException;
import com.retail.store.exception.CustomerException;
import com.retail.store.exception.ItemException;
import com.retail.store.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RetailStoreControllerAdvice {

    private final static String FAILURE = "FAILURE";

    @ExceptionHandler(BillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBillException(BillException e) {
        return new ErrorResponse(FAILURE, e.getMessage());
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBillException(CustomerException e) {
        return new ErrorResponse(FAILURE, e.getMessage());
    }

    @ExceptionHandler(ItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBillException(ItemException e) {
        return new ErrorResponse(FAILURE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(FAILURE, e.getMessage());
    }
}

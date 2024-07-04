package com.retail.store.controller;

import com.retail.store.exception.BillException;
import com.retail.store.exception.CustomerException;
import com.retail.store.exception.ItemException;
import com.retail.store.model.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RetailStoreControllerAdvice {

    private final static String FAILURE = "FAILURE";

    @ExceptionHandler(BillException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Object> handleBillException(BillException e) {
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.FAILURE, e.getMessage(), null);
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Object> handleBillException(CustomerException e) {
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.FAILURE, e.getMessage(), null);
    }

    @ExceptionHandler(ItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<Object> handleBillException(ItemException e) {
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.FAILURE, e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<Object> handleException(Exception e) {
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.FAILURE, e.getMessage(), null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ServiceResponse<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.FAILURE, methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }
}

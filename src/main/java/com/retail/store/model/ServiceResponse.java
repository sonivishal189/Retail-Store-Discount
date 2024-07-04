package com.retail.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {

    private ServiceResponseStatus status;
    private String message;
    private T body;

    public enum ServiceResponseStatus {
        SUCCESS,
        FAILURE
    }
}

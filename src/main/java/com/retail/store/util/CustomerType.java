package com.retail.store.util;

public enum CustomerType {
    EMPLOYEE("Employee"),
    AFFILIATE("Affiliate"),
    REGULAR("Regular");

    CustomerType(String employee) {
    }

    public String getCustomerType() {
        return this.name();
    }

}

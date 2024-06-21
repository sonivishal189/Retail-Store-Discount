package com.retail.store.util;

public enum CustomerType {
    EMPLOYEE("Employee"),
    AFFILIATE("Affiliate"),
    CUSTOMER("Customer");

    CustomerType(String employee) {
    }

    public String getCustomerType() {
        return this.name();
    }

}

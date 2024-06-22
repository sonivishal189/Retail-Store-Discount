package com.retail.store.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateBillRequest {
    private String customerId;
    private List<BillItem> items;
}

package com.retail.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillItem {

    private int itemId;
    private int quantity;

}

package com.retail.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class LineItem {

    @Id
    private int itemId;
    private String itemName;
    private double pricePerUnit;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private Bill bill;

    public LineItem(int itemId, String itemName, double pricePerUnit, int quantity, Bill bill) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.pricePerUnit = pricePerUnit;
        this.price = pricePerUnit * quantity;
        this.quantity = quantity;
        this.bill = bill;
    }
}

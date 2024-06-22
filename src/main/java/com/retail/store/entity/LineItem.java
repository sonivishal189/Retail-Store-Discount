package com.retail.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class LineItem {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int lineItemId;
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

    @Override
    public String toString() {
        return "LineItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

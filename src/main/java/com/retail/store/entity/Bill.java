package com.retail.store.entity;

import com.retail.store.util.PaymentMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue
    private int id;

    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    @OneToMany
    private List<Item> items;

    private double billAmount;

    private double discount;

    private Date billDate;

    private PaymentMode paymentMode;

}

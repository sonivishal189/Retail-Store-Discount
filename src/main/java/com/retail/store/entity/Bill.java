package com.retail.store.entity;

import com.retail.store.util.PaymentMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull(message = "Customer Id cannot be null")
    private String customerId;

    private String customerName;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Line Item cannot be null")
    private List<LineItem> LineItems;

    private double billAmount;

    private double discount;

    private Date billDate;

    private PaymentMode paymentMode;

}

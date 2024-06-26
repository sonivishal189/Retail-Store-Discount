package com.retail.store.entity;

import com.retail.store.util.PaymentMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(hidden = true)
    private int billId;

    @NotNull(message = "Customer Id cannot be null")
    private String customerId;

    private String customerName;

    private String customerType;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "Line Item cannot be null")
    private List<LineItem> LineItems;

    private double billAmount;

    private double customerTypeDiscount;

    private double billAmountDiscount;

    private double netDiscount;

    private double dueAmount;

    private double netPayableAmount;

    private Date billDate;

    private PaymentMode paymentMode;

}

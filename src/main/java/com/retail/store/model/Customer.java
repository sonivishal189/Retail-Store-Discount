package com.retail.store.model;

import com.retail.store.util.CustomerType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Customer {

    @Id
    private int id;
    private String name;
    private CustomerType customerType;
    private Date joiningDate;

}

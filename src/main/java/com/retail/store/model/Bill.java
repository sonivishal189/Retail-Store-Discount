package com.retail.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Bill {

    @Id
    private int id;
}

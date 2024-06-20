package com.retail.store.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Item {

    @Id
    private int id;
}

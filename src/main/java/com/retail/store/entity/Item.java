package com.retail.store.entity;

import com.retail.store.util.ItemType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotNull(message = "Item Name cannot be null or empty")
    private String name;

    @Range(min = 1, message = "Item Price cannot be less than 1")
    private double price;

    private String description;

    @NotNull(message = "Item Type cannot be null or empty")
    private ItemType itemType;

}

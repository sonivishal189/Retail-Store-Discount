package com.retail.store.entity;

import com.retail.store.util.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    @Schema(hidden = true)
    private int itemId;

    @NotNull(message = "Item Name cannot be null or empty")
    private String name;

    @Range(min = 1, message = "Item Price cannot be less than 1")
    private double price;

    private String description;

    @NotNull(message = "Item Type cannot be null or empty")
    private ItemType itemType;

}

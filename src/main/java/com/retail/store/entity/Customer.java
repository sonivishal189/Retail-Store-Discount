package com.retail.store.entity;

import com.retail.store.util.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @Schema(hidden = true)
    private String customerId;

    @NotNull(message = "Phone Number cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotNull(message = "Name cannot be null or empty")
    private String name;

    @NotNull(message = "Customer Type cannot be null or empty")
    private CustomerType customerType;

    @Schema(hidden = true)
    private LocalDate joiningDate;

    @Email(message = "Invalid Email")
    private String email;

    public Customer(String phoneNumber, String name, CustomerType customerType, LocalDate joiningDate, String email) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.customerType = customerType;
        this.joiningDate = joiningDate;
        this.email = email;
    }

}

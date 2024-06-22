package com.retail.store.entity;

import com.retail.store.util.CustomerType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer implements Serializable {

    @Id
    private String id;

    @NotNull(message = "Phone Number cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotNull(message = "Name cannot be null or empty")
    private String name;

    @NotNull(message = "Customer Type cannot be null or empty")
    private CustomerType customerType;

    @NotNull(message = "Joining Date cannot be null or empty")
    private Date joiningDate;

    @Email(message = "Invalid Email")
    private String email;

}

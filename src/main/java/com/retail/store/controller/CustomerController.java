package com.retail.store.controller;

import com.retail.store.entity.Customer;
import com.retail.store.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
@Tag(name = "Customer Management", description = "APIs to manage Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    @Operation(summary = "Get All Customer")
    public List<Customer> getAllCustomers() {
        log.info("Get All Customers");
        return customerService.getAllCustomers();
    }

    @PostMapping("/create")
    @Operation(summary = "Create new Customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        log.info("Create Customer: {}", customer);
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by Id")
    public Customer getCustomerById(@PathVariable String id) {
        log.info("Get Customer By Id: {}", id);
        return customerService.getCustomerById(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update Customer")
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        log.info("Update Customer id: {}, customer: {}", id, customer);
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer by Id")
    public String deleteCustomer(@PathVariable String id) {
        log.info("Delete Customer id: {}", id);
        return customerService.deleteCustomer(id);
    }
}

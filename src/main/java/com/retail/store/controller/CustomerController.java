package com.retail.store.controller;

import com.retail.store.entity.Customer;
import com.retail.store.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        log.info("Get All Customers");
        return customerService.getAllCustomers();
    }

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        log.info("Create Customer: {}", customer);
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        log.info("Get Customer By Id: {}", id);
        return customerService.getCustomerById(id);
    }
}

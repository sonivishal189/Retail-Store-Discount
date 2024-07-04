package com.retail.store.controller;

import com.retail.store.entity.Customer;
import com.retail.store.model.ServiceResponse;
import com.retail.store.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ServiceResponse<List<Customer>> getAllCustomers() {
        log.info("Get All Customers");
        List<Customer> allCustomers = customerService.getAllCustomers();
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, allCustomers);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new Customer")
    public ServiceResponse<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        log.info("Create Customer: {}", customer);
        Customer newCustomer = customerService.createCustomer(customer);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, newCustomer);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Customer by Id")
    public ServiceResponse<Customer> getCustomerById(@PathVariable String id) {
        log.info("Get Customer By Id: {}", id);
        Customer customerById = customerService.getCustomerById(id);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, customerById);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update Customer")
    public ServiceResponse<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        log.info("Update Customer id: {}, customer: {}", id, customer);
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer by Id")
    public ServiceResponse<String> deleteCustomer(@PathVariable String id) {
        log.info("Delete Customer id: {}", id);
        String deleteMessage = customerService.deleteCustomer(id);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, deleteMessage, null);
    }
}

package com.retail.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/all")
    public String getAllCustomers() {
        log.info("All Customers");
        return "All Customers";
    }
}

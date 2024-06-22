package com.retail.store.service;

import com.retail.store.dao.CustomerRepository;
import com.retail.store.entity.Customer;
import com.retail.store.exception.CustomerException;
import com.retail.store.util.CustomerType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> allCustomer = (List<Customer>) customerRepository.findAll();
        log.info("All customers: {}", allCustomer);
        return allCustomer;
    }

    public Customer createCustomer(Customer customer) {
        customer.setId(customer.getPhoneNumber());
        customer.setJoiningDate(new Date());
        customer = customerRepository.save(customer);
        log.info("Created customer: {}", customer);
        return customer;
    }

    public Customer getCustomerById(String id) {
        if (customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();
            log.info("Customer fetched by id: {}", customer);
            return customer;
        }
        log.error("Customer not found with id: {}", id);
        throw new CustomerException("Customer not found with id: " + id);
    }

    @PostConstruct
    private void init() {
        List<Customer> customerList = List.of(
                new Customer("1", "9876510000", "John", CustomerType.REGULAR, new Date(), "john@email.com"),
                new Customer("2", "9876510001", "Jenny", CustomerType.REGULAR, new Date(), "jenny@email.com"),
                new Customer("3", "9876520000", "Tim", CustomerType.AFFILIATE, new Date(), "tim@email.com"),
                new Customer("4", "9876520001", "Tom", CustomerType.AFFILIATE, new Date(), "tom@email.com"),
                new Customer("5", "9876530000", "Alex", CustomerType.EMPLOYEE, new Date(), "alex@email.com"),
                new Customer("6", "9876540001", "Alina", CustomerType.EMPLOYEE, new Date(), "alina@email.com")
        );

        for (Customer customer : customerList) {
            createCustomer(customer);
        }
    }
}

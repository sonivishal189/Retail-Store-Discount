package com.retail.store.service;

import com.retail.store.dao.CustomerRepository;
import com.retail.store.exception.CustomerException;
import com.retail.store.entity.Customer;
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
        customer.setJoiningDate(new Date());
        customer = customerRepository.save(customer);
        log.info("Created customer: {}", customer);
        return customer;
    }

    public Customer getCustomerById(int id) {
        if(customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();
            log.info("Customer fetched by id: {}", customer);
            return customer;
        }
        log.error("Customer not found with id: {}", id);
        throw new CustomerException("Customer not found with id: " + id);
    }
}

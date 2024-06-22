package com.retail.store.service;

import com.retail.store.dao.CustomerRepository;
import com.retail.store.entity.Customer;
import com.retail.store.exception.CustomerException;
import com.retail.store.util.CustomerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
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
        if (customerRepository.findById(customer.getPhoneNumber()).isPresent()) {
            log.error("Customer already exists with phone number: {}", customer.getPhoneNumber());
            throw new CustomerException("Customer already exists with phone number: " + customer.getPhoneNumber());
        }
        customer.setCustomerId(customer.getPhoneNumber());
        if (null == customer.getJoiningDate()) {
            customer.setJoiningDate(LocalDate.now());
        }
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

    public Customer updateCustomer(String id, Customer customer) {
        Customer customerInDb = getCustomerById(id);
        if (customer.getName() != null && !customer.getName().isEmpty()) {
            customerInDb.setName(customer.getName());
        }
        if (customer.getCustomerType() != null) {
            customerInDb.setCustomerType(customer.getCustomerType());
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            customerInDb.setEmail(customer.getEmail());
        }
        if (null != customer.getPhoneNumber() && !customerInDb.getPhoneNumber().equals(customer.getPhoneNumber())) {
            log.error("Phone number cannot be changed");
            throw new CustomerException("Phone number cannot be changed");
        }
        if (null != customer.getJoiningDate() && customerInDb.getJoiningDate() != customer.getJoiningDate()) {
            log.error("Joining date cannot be changed");
            throw new CustomerException("Joining date cannot be changed");
        }
        customerInDb = customerRepository.save(customerInDb);
        log.info("Updated customer: {}", customerInDb);
        return customerInDb;
    }

    public String deleteCustomer(String id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
        log.info("Deleted customer with id: {}", id);
        return "Deleted customer with id: " + id;
    }

    @PostConstruct
    private void init() {
        List<Customer> customerList = List.of(
                new Customer("9999991001", "John", CustomerType.REGULAR, LocalDate.now(), "john@email.com"),
                new Customer("9999991002", "Jenny", CustomerType.REGULAR, LocalDate.of(2013, 10, 9), "jenny@email.com"),
                new Customer("9999992001", "Tim", CustomerType.AFFILIATE, LocalDate.now(), "tim@email.com"),
                new Customer("9999992002", "Tom", CustomerType.AFFILIATE, LocalDate.of(2014, 11, 7), "tom@email.com"),
                new Customer("9999993001", "Alex", CustomerType.EMPLOYEE, LocalDate.now(), "alex@email.com"),
                new Customer("9999993002", "Alina", CustomerType.EMPLOYEE, LocalDate.of(2015, 3, 3), "alina@email.com")
        );

        for (Customer customer : customerList) {
            createCustomer(customer);
        }
    }
}

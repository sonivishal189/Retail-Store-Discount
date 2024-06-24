package com.retail.store.service;

import com.retail.store.dao.CustomerRepository;
import com.retail.store.entity.Customer;
import com.retail.store.exception.CustomerException;
import com.retail.store.util.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    public static final String CUSTOMER_ID = "1111111111";
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);
        customer.setPhoneNumber(CUSTOMER_ID);
        customer.setName("John");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setJoiningDate(LocalDate.now());
        customer.setEmail("john@email.com");
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> allCustomers = customerService.getAllCustomers();

        assertNotNull(allCustomers);
        assertEquals(1, allCustomers.size());
    }

    @Test
    void testCreateCustomerSuccess() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        assertEquals(CUSTOMER_ID, createdCustomer.getCustomerId());
    }

    @Test
    void testCreateCustomerAlreadyExists() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.createCustomer(customer));
        assertTrue(exception.getMessage().contains("Customer already exists"));
    }

    @Test
    void testGetCustomerByIdSuccess() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomerById(CUSTOMER_ID);

        assertNotNull(foundCustomer);
        assertEquals(CUSTOMER_ID, foundCustomer.getCustomerId());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.getCustomerById(CUSTOMER_ID));
        assertTrue(exception.getMessage().contains("Customer not found with id:"));
    }

    @Test
    void testUpdateCustomerSuccess() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(CUSTOMER_ID, customer);

        assertNotNull(updatedCustomer);
        assertEquals(CUSTOMER_ID, updatedCustomer.getCustomerId());
    }

    @Test
    void testUpdateCustomerPhoneNumberChange() {
        Customer newCustomer = new Customer();
        newCustomer.setPhoneNumber("9999991002");

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.updateCustomer(CUSTOMER_ID, newCustomer));
        assertTrue(exception.getMessage().contains("Phone number cannot be changed"));
    }

    @Test
    void testUpdateCustomerJoiningDateChange() {
        Customer newCustomer = new Customer();
        newCustomer.setJoiningDate(LocalDate.of(2015, 1, 1));

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.updateCustomer(CUSTOMER_ID, newCustomer));
        assertTrue(exception.getMessage().contains("Joining date cannot be changed"));
    }

    @Test
    void testDeleteCustomerSuccess() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        String response = customerService.deleteCustomer(CUSTOMER_ID);

        assertTrue(response.contains("Deleted customer with id"));
    }

    @Test
    void testDeleteCustomerNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.deleteCustomer(CUSTOMER_ID));
        assertTrue(exception.getMessage().contains("Customer not found with id:"));
    }

    @Test
    void testInt() {
        customerService.init();
    }
}

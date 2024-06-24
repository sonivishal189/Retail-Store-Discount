package com.retail.store.service;

import com.retail.store.dao.BillRepository;
import com.retail.store.entity.Bill;
import com.retail.store.entity.Customer;
import com.retail.store.entity.Item;
import com.retail.store.entity.LineItem;
import com.retail.store.exception.BillException;
import com.retail.store.model.BillItem;
import com.retail.store.util.CustomerType;
import com.retail.store.util.ItemType;
import com.retail.store.util.PaymentMode;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    public static final String CUSTOMER_ID = "1111111111";
    @Mock
    private BillRepository billRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private BillService billService;

    private Bill bill;
    private Customer customer;
    private List<BillItem> billItems;

    @BeforeEach
    void setUp() {
        bill = new Bill();
        bill.setBillId(1);
        bill.setCustomerId(CUSTOMER_ID);
        bill.setBillAmount(400.00);
        LineItem lineItem = new LineItem();
        lineItem.setItemId(1);
        lineItem.setItemName("TEST_ITEM");
        lineItem.setPricePerUnit(200.00);
        lineItem.setLinePrice(400.00);
        lineItem.setQuantity(2);
        bill.setLineItems(List.of(lineItem));

        customer = new Customer();
        customer.setCustomerId(CUSTOMER_ID);
        customer.setPhoneNumber(CUSTOMER_ID);
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setName("TEST_CUSTOMER");
        customer.setJoiningDate(LocalDate.now());

        billItems = new ArrayList<>();
        BillItem billItem = new BillItem();
        billItem.setItemId(1);
        billItem.setQuantity(2);
        billItems.add(billItem);
    }

    @Test
    void testGetBillByIdSuccess() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        Bill foundBill = billService.getBillById(1);

        assertNotNull(foundBill);
        assertEquals(1, foundBill.getBillId());
        verify(billRepository, times(2)).findById(1);
    }

    @Test
    void testGetBillByIdNotFound() {
        when(billRepository.findById(1)).thenReturn(Optional.empty());

        BillException exception = assertThrows(BillException.class, () -> billService.getBillById(1));
        assertEquals("Bill not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateBillSuccess() {
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemById(1)).thenReturn(new Item());
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        Bill createdBill = billService.createBill(CUSTOMER_ID, billItems);

        assertNotNull(createdBill);
        assertEquals(CUSTOMER_ID, createdBill.getCustomerId());
    }

    @Test
    void testCalculateDiscountForOldRegularCustomerNonGroceryItem() {
        customer.setJoiningDate(LocalDate.now().minusYears(3));
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemTypeById(1)).thenReturn(ItemType.OTHER);
        billService.calculateDiscount(bill);

        assertEquals(20.0, bill.getCustomerTypeDiscount());
        assertEquals(15.0, bill.getBillAmountDiscount());
        assertEquals(35.0, bill.getNetDiscount());
    }

    @Test
    void testCalculateDiscountForNewRegularCustomerGroceryItem() {
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemTypeById(1)).thenReturn(ItemType.GROCERY);
        billService.calculateDiscount(bill);

        assertEquals(0.0, bill.getCustomerTypeDiscount());
        assertEquals(20.0, bill.getBillAmountDiscount());
        assertEquals(20.0, bill.getNetDiscount());
    }

    @Test
    void testCalculateDiscountForEmployeeCustomer() {
        customer.setCustomerType(CustomerType.EMPLOYEE);
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemTypeById(1)).thenReturn(ItemType.OTHER);
        billService.calculateDiscount(bill);

        assertEquals(120.0, bill.getCustomerTypeDiscount());
        assertEquals(10.0, bill.getBillAmountDiscount());
        assertEquals(130.0, bill.getNetDiscount());
    }

    @Test
    void testCalculateDiscountForAffiliateCustomer() {
        customer.setCustomerType(CustomerType.AFFILIATE);
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemTypeById(1)).thenReturn(ItemType.OTHER);
        billService.calculateDiscount(bill);

        assertEquals(40.0, bill.getCustomerTypeDiscount());
        assertEquals(15.0, bill.getBillAmountDiscount());
        assertEquals(55.0, bill.getNetDiscount());
    }

    @Test
    void testAddItemInExistingBillAlreadyPaid() {
        bill.setPaymentMode(PaymentMode.CASH);
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        BillException exception = assertThrows(BillException.class, () -> billService.addItemInExistingBill(1, new BillItem()));
        assertTrue(exception.getMessage().contains("already paid, please create a new bill"));
    }

    @Test
    void testAddItemInExistingNotPaidBill() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);
        when(itemService.getItemById(2)).thenReturn(new Item());

        BillItem billItem = new BillItem();
        billItem.setItemId(2);
        billItem.setQuantity(2);

        billService.addItemInExistingBill(1, billItem);
        assertEquals(2, bill.getLineItems().size());

    }

    @Test
    void testPayBill() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        billService.payBill(1, PaymentMode.CASH);
        assertEquals(PaymentMode.CASH, bill.getPaymentMode());
    }

    @Test
    void testRemoveItemFromExistingBill() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        when(customerService.getCustomerById(CUSTOMER_ID)).thenReturn(customer);

        billService.removeItemFromExistingBill(1, 1);
    }

    @Test
    void testRemoveWrongItemFromExistingBill() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        BillException exception = assertThrows(BillException.class, () -> billService.removeItemFromExistingBill(1, 2));
        assertTrue(exception.getMessage().contains("Item not found in bill:"));
    }

    @Test
    void testDeleteBillSuccess() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));

        String response = billService.deleteBill(1);

        assertEquals("Bill deleted with id: 1", response);
        verify(billRepository, times(1)).delete(bill);
    }

    @Test
    void testDeleteBillAlreadyPaid() {
        bill.setPaymentMode(PaymentMode.CREDIT_CARD);
        when(billRepository.findById(bill.getBillId())).thenReturn(Optional.of(bill));

        BillException exception = assertThrows(BillException.class, () -> billService.deleteBill(bill.getBillId()));
        assertTrue(exception.getMessage().contains("already paid cannot be deleted"));
    }

    @Test
    void testGetBillDiscount() {
        when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        double discount = billService.getBillDiscount(1);

        assertEquals(0.0, discount);
    }
}


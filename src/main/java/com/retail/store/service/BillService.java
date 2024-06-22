package com.retail.store.service;

import com.retail.store.dao.BillRepository;
import com.retail.store.entity.Bill;
import com.retail.store.entity.Customer;
import com.retail.store.entity.Item;
import com.retail.store.entity.LineItem;
import com.retail.store.exception.BillException;
import com.retail.store.model.BillItem;
import com.retail.store.util.PaymentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    public Bill getBillById(int id) {
        if (billRepository.findById(id).isPresent()) {
            Bill bill = billRepository.findById(id).get();
            log.info("Bill by id: {}", bill);
            return bill;
        }
        log.error("Bill not found with id: {}", id);
        throw new BillException("Bill not found with id: " + id);
    }

    public Bill createBill(String customerId, List<BillItem> billItems) {
        Bill newBill = new Bill();
        Customer customer = customerService.getCustomerById(customerId);
        newBill.setCustomerId(customer.getId());
        newBill.setCustomerName(customer.getName());
        newBill.setLineItems(new ArrayList<>());
        newBill.setPaymentMode(null);
        newBill.setBillDate(new Date());
        for (BillItem billItem : billItems) {
            addItem(billItem.getItemId(), billItem.getQuantity(), newBill, customer);
        }
        int billId = billRepository.save(newBill).getId();
        log.info("Created bill with id: {}", billId);
        return newBill;
    }

    private void addItem(int itemId, int quantity, Bill bill, Customer customer) {
        Item item = itemService.getItemById(itemId);
        bill.getLineItems().add(new LineItem(itemId, item.getName(), item.getPrice(), quantity, bill));
        bill.setBillAmount(bill.getBillAmount() + (item.getPrice() * quantity));
        populateDiscount(bill, customer);
    }

    public Bill addItemToBill(int billId, BillItem billItem) {
        Bill bill = getBillById(billId);
        Customer customer = customerService.getCustomerById(bill.getCustomerId());
        addItem(billItem.getItemId(), billItem.getQuantity(), bill, customer);
        bill = billRepository.save(bill);
        log.info("Added item to bill: {}", bill);
        return bill;
    }

    public Bill payBill(int billId, PaymentMode paymentMode) {
        Bill bill = getBillById(billId);
        bill.setPaymentMode(paymentMode);
        bill = billRepository.save(bill);
        log.info("Paid bill: {}", bill);
        return bill;
    }

    private void populateDiscount(Bill bill, Customer customer) {
        bill.setDiscount(0.0);
    }
}

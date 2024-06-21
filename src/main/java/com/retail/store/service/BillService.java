package com.retail.store.service;

import com.retail.store.dao.BillRepository;
import com.retail.store.exception.BillException;
import com.retail.store.entity.Bill;
import com.retail.store.entity.Customer;
import com.retail.store.entity.Item;
import com.retail.store.util.PaymentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public int createBill(int customerId) {
        Bill newBill = new Bill();
        Customer customer = customerService.getCustomerById(customerId);
        newBill.setCustomer(customer);
        newBill.setBillAmount(0.0);
        newBill.setDiscount(0.0);
        newBill.setBillDate(new Date());
        int billId = billRepository.save(newBill).getId();
        log.info("Created bill with id: {}", billId);
        return billId;
    }

    public Bill addItemToBill(int billId, int itemId) {
        Bill bill = getBillById(billId);
        Item item = itemService.getItemById(itemId);
        bill.getItems().add(item);
        bill.setBillAmount(bill.getBillAmount() + item.getPrice());
        // TODO: Add discount based on condition given
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
}

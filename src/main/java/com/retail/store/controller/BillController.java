package com.retail.store.controller;

import com.retail.store.entity.Bill;
import com.retail.store.service.BillService;
import com.retail.store.util.PaymentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create/{customerId}")
    public int createBill(@PathVariable int customerId) {
        log.info("Create Bill for Customer: {}", customerId);
        return billService.createBill(customerId);
    }

    @PatchMapping("/addItem/{billId}/{itemId}")
    public Bill addItemToBill(@PathVariable int billId, @PathVariable int itemId) {
        log.info("Add Item: {} to Bill: {}", itemId, billId);
        return billService.addItemToBill(billId, itemId);
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable int id) {
        log.info("Get Bill By Id: {}", id);
        return billService.getBillById(id);
    }

    @PatchMapping("/payment/{billId}/{paymentMode}")
    public Bill payBill(@PathVariable int billId, @PathVariable PaymentMode paymentMode) {
        log.info("Pay Bill: {}, mode: {}", billId, paymentMode);
        return billService.payBill(billId, paymentMode);
    }
}

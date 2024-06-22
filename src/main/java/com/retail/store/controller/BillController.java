package com.retail.store.controller;

import com.retail.store.entity.Bill;
import com.retail.store.model.BillItem;
import com.retail.store.model.CreateBillRequest;
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

    @PostMapping("/create")
    public Bill createBill(@RequestBody CreateBillRequest billRequest) {
        log.info("Create Bill: {}", billRequest);
        return billService.createBill(billRequest.getCustomerId(), billRequest.getItems());
    }

    @PatchMapping("/addItem/{billId}")
    public Bill addItemToBill(@PathVariable int billId, @RequestBody BillItem billItem) {
        log.info("Add Item: {} to Bill: {}", billItem, billId);
        return billService.addItemToBill(billId, billItem);
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

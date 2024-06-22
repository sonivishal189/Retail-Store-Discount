package com.retail.store.controller;

import com.retail.store.entity.Bill;
import com.retail.store.model.BillItem;
import com.retail.store.model.CreateBillRequest;
import com.retail.store.service.BillService;
import com.retail.store.util.PaymentMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bill")
@Tag(name = "Bill Management", description = "APIs to manage Bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/create")
    @Operation(summary = "Generate new bill")
    public Bill createBill(@RequestBody CreateBillRequest billRequest) {
        log.info("Create Bill: {}", billRequest);
        return billService.createBill(billRequest.getCustomerId(), billRequest.getItems());
    }

    @PatchMapping("/addItem/{billId}")
    @Operation(summary = "Add Item in existing bill")
    public Bill addItemInExistingBill(@PathVariable int billId, @RequestBody BillItem billItem) {
        log.info("Add Item: {} to Bill: {}", billItem, billId);
        return billService.addItemInExistingBill(billId, billItem);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Bill by Id")
    public Bill getBillById(@PathVariable int id) {
        log.info("Get Bill By Id: {}", id);
        return billService.getBillById(id);
    }

    @PatchMapping("/payment/{billId}/{paymentMode}")
    @Operation(summary = "Pay Bill amount")
    public Bill payBill(@PathVariable int billId, @PathVariable PaymentMode paymentMode) {
        log.info("Pay Bill: {}, mode: {}", billId, paymentMode);
        return billService.payBill(billId, paymentMode);
    }

    @PatchMapping("/removeItem/{billId}/{itemId}")
    @Operation(summary = "Remove Item from Bill")
    public Bill removeItemFromExistingBill(@PathVariable int billId, @PathVariable int itemId) {
        log.info("Remove Item: {} from Bill: {}", itemId, billId);
        return billService.removeItemFromExistingBill(billId, itemId);
    }

    @DeleteMapping("/{billId}")
    @Operation(summary = "Delete Bill")
    public String deleteBill(@PathVariable int billId) {
        log.info("Delete Bill: {}", billId);
        return billService.deleteBill(billId);
    }
}

package com.retail.store.controller;

import com.retail.store.entity.Bill;
import com.retail.store.model.BillItem;
import com.retail.store.model.CreateBillRequest;
import com.retail.store.model.ServiceResponse;
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
    public ServiceResponse<Bill> createBill(@RequestBody CreateBillRequest billRequest) {
        log.info("Create Bill: {}", billRequest);
        Bill bill = billService.createBill(billRequest.getCustomerId(), billRequest.getItems());
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, bill);
    }

    @PatchMapping("/addItem/{billId}")
    @Operation(summary = "Add Item in existing bill")
    public ServiceResponse<Bill> addItemInExistingBill(@PathVariable int billId, @RequestBody BillItem billItem) {
        log.info("Add Item: {} to Bill: {}", billItem, billId);
        Bill bill = billService.addItemInExistingBill(billId, billItem);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, bill);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Bill by Id")
    public ServiceResponse<Bill> getBillById(@PathVariable int id) {
        log.info("Get Bill By Id: {}", id);
        Bill billById = billService.getBillById(id);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, billById);
    }

    @PatchMapping("/payment/{billId}/{paymentMode}")
    @Operation(summary = "Pay Bill amount")
    public ServiceResponse<Bill> payBill(@PathVariable int billId, @PathVariable PaymentMode paymentMode) {
        log.info("Pay Bill: {}, mode: {}", billId, paymentMode);
        Bill bill = billService.payBill(billId, paymentMode);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, bill);
    }

    @PatchMapping("/removeItem/{billId}/{itemId}")
    @Operation(summary = "Remove Item from Bill")
    public ServiceResponse<Bill> removeItemFromExistingBill(@PathVariable int billId, @PathVariable int itemId) {
        log.info("Remove Item: {} from Bill: {}", itemId, billId);
        Bill bill = billService.removeItemFromExistingBill(billId, itemId);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, null, bill);
    }

    @DeleteMapping("/{billId}")
    @Operation(summary = "Delete Bill")
    public ServiceResponse<String> deleteBill(@PathVariable int billId) {
        log.info("Delete Bill: {}", billId);
        String deleteMessage = billService.deleteBill(billId);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, deleteMessage, null);
    }

    @PostMapping("/{billId}")
    @Operation(summary = "Get Bill Discount")
    public ServiceResponse<Double> getBillDiscount(@PathVariable int billId) {
        log.info("Get Discount for Bill Id: {}", billId);
        double billDiscount = billService.getBillDiscount(billId);
        return new ServiceResponse<>(ServiceResponse.ServiceResponseStatus.SUCCESS, "Net Discount Amount", billDiscount);
    }
}

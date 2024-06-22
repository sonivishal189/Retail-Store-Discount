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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

//        Optional<Bill> existingNotPaidBill = billRepository.findByCustomerIdAndPaymentModeIsNull(customerId);
//        if (existingNotPaidBill.isPresent()) {
//            log.error("Bill already exists with billId: {}, for customerId: {}", existingNotPaidBill.get().getBillId(), customerId);
//            throw new BillException("Bill already exists with id: " + existingNotPaidBill.get().getBillId() + " for customerId: " + customerId);
//        }

        Customer customer = customerService.getCustomerById(customerId);
        newBill.setCustomerId(customer.getCustomerId());
        newBill.setCustomerName(customer.getName());
        newBill.setCustomerType(customer.getCustomerType().name());
        newBill.setLineItems(new ArrayList<>());
        newBill.setPaymentMode(null);
        newBill.setBillDate(new Date());
        for (BillItem billItem : billItems) {
            addItem(billItem.getItemId(), billItem.getQuantity(), newBill, customer);
        }
        int billId = billRepository.save(newBill).getBillId();
        log.info("Created bill with id: {}", billId);
        return newBill;
    }

    private void addItem(int itemId, int quantity, Bill bill, Customer customer) {
        Item item = itemService.getItemById(itemId);
        bill.getLineItems().add(new LineItem(itemId, item.getName(), item.getPrice(), quantity, bill));
        bill.setBillAmount(bill.getBillAmount() + (item.getPrice() * quantity));
        calculateDiscount(bill, customer);
    }

    public Bill addItemToBill(int billId, BillItem billItem) {
        Bill bill = getBillById(billId);
        throwErrorIfPaymentCompleted(bill);
        Customer customer = customerService.getCustomerById(bill.getCustomerId());
        addItem(billItem.getItemId(), billItem.getQuantity(), bill, customer);
        bill = billRepository.save(bill);
        log.info("Added item to bill: {}", bill);
        return bill;
    }

    private static void throwErrorIfPaymentCompleted(Bill bill) {
        if (null != bill.getPaymentMode()) {
            log.error("Bill already paid: {}", bill.getBillId());
            throw new BillException("Bill already paid: " + bill.getBillId());
        }
    }

    public Bill payBill(int billId, PaymentMode paymentMode) {
        Bill bill = getBillById(billId);
        bill.setPaymentMode(paymentMode);
        bill.setDueAmount(0.0);
        bill = billRepository.save(bill);
        log.info("Paid bill: {}", bill);
        return bill;
    }

    private void calculateDiscount(Bill bill, Customer customer) {
        log.info("Discount calculation starts for billId: {}", bill.getBillId());
        double discountPercentage = 0.0;
        if (customer.getCustomerType() == CustomerType.EMPLOYEE) {
            discountPercentage = 0.30;
        } else if (customer.getCustomerType() == CustomerType.AFFILIATE) {
            discountPercentage = 0.10;
        } else if (customer.getCustomerType() == CustomerType.REGULAR && customer.getJoiningDate().isBefore(LocalDate.now().minusYears(2))) {
            discountPercentage += 0.05;
            log.info("Customer joined before 2 years");
        }
        log.info("Customer is of type {} and eligible for {}% discount", customer.getCustomerType(), discountPercentage * 100);

        double totalDiscount = 0.0;

        for (LineItem lineItem : bill.getLineItems()) {
            double discount = 0.0;
            ItemType itemType = itemService.getItemTypeById(lineItem.getItemId());
            if (itemType != ItemType.GROCERY) {
                discount = lineItem.getLinePrice() * discountPercentage;
                totalDiscount += discount;
            }
            lineItem.setLineDiscount(discount);
            lineItem.setFinalLinePrice(lineItem.getLinePrice() - discount);
        }

        bill.setCustomerTypeDiscount(totalDiscount);

        double discount = 0.0;
        if (bill.getBillAmount() - totalDiscount >= 100.00) {
            log.info("Bill amount is more than 100.00, eligible for Bill Amount Discount");
            int discountMultiple = (int) ((bill.getBillAmount() - totalDiscount) / 100.00);
            discount = discountMultiple * 5.0;
            totalDiscount += discount;
        }

        bill.setBillAmountDiscount(discount);
        bill.setTotalDiscount(Double.parseDouble(String.format("%.2f", totalDiscount)));
        bill.setFinalBillAmount(bill.getBillAmount() - bill.getTotalDiscount());
        bill.setDueAmount(bill.getFinalBillAmount());
        log.info("Discount calculation ends for billId: {}", bill.getBillId());
    }

    public Bill removeItemFromBill(int billId, int itemId) {
        Bill bill = getBillById(billId);
        throwErrorIfPaymentCompleted(bill);
        if (bill.getLineItems().stream().noneMatch(lineItem -> lineItem.getItemId() == itemId)) {
            log.error("Item not found in bill: {}", billId);
            throw new BillException("Item not found in bill: " + billId);
        }
        bill.getLineItems().removeIf(lineItem -> lineItem.getItemId() == itemId);
        calculateDiscount(bill, customerService.getCustomerById(bill.getCustomerId()));

        bill = billRepository.save(bill);
        log.info("Removed item from bill: {}", bill);
        return bill;
    }

    public String deleteBill(int billId) {
        Bill bill = getBillById(billId);
        if(null != bill.getPaymentMode()) {
            log.error("Bill: {} already paid cannot be delete", billId);
            throw new BillException("Bill " + billId + " already paid cannot be delete");
        }
        billRepository.delete(bill);
        log.info("Deleted bill: {}", bill);
        return "Bill deleted with id: " + billId;
    }
}

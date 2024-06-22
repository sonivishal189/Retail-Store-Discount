package com.retail.store.dao;

import com.retail.store.entity.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends CrudRepository<Bill, Integer> {
    Optional<Bill> findByCustomerIdAndPaymentModeIsNull(String customerId);
}

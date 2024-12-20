package com.maxeriksson.BillingManagement.repository;

import com.maxeriksson.BillingManagement.model.Bill;
import com.maxeriksson.BillingManagement.model.BillId;

import org.springframework.data.jpa.repository.JpaRepository;

/** BillRepository */
public interface BillRepository extends JpaRepository<Bill, BillId> {}

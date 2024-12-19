package com.maxeriksson.BillingManagement.repository;

import com.maxeriksson.BillingManagement.model.Customer;
import com.maxeriksson.BillingManagement.model.SocialSecurityNumber;

import org.springframework.data.jpa.repository.JpaRepository;

/** CustomerRepository */
public interface CustomerRepository extends JpaRepository<Customer, SocialSecurityNumber> {}

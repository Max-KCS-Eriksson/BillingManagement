package com.maxeriksson.BillingManagement.repository;

import com.maxeriksson.BillingManagement.model.Service;

import org.springframework.data.jpa.repository.JpaRepository;

/** ServiceRepository */
public interface ServiceRepository extends JpaRepository<Service, String> {}

package com.projectx.customer.repository;

import com.projectx.customer.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
}

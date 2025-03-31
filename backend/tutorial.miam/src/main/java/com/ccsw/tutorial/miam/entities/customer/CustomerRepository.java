package com.ccsw.tutorial.miam.entities.customer;

import com.ccsw.tutorial.miam.entities.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByNameIgnoreCase(String name);
}

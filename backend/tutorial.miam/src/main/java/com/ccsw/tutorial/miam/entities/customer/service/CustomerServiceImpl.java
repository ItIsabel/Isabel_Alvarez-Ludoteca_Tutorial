package com.ccsw.tutorial.miam.entities.customer.service;

import com.ccsw.tutorial.miam.entities.customer.CustomerRepository;
import com.ccsw.tutorial.miam.entities.customer.model.Customer;
import com.ccsw.tutorial.miam.entities.customer.model.CustomerDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author miam
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer get(Long id) {

        return this.customerRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> findAll() {

        return (List<Customer>) this.customerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, CustomerDto dto) throws Exception {
        Customer customer;

        //Hago una búsqueda en la bbdd con el nombre del dto. y verifico si la lista que da está vacía
        if (customerRepository.findByNameIgnoreCase(dto.getName()).isEmpty()) {
            if (id == null) {
                customer = new Customer();
            } else {
                customer = this.get(id);
            }
            customer.setName(dto.getName());
            this.customerRepository.save(customer);

        } else {

            throw new Exception("El nombre ya existe");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.customerRepository.deleteById(id);
    }
}

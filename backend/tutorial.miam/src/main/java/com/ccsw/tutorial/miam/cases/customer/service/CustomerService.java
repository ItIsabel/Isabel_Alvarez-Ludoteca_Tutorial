package com.ccsw.tutorial.miam.cases.customer.service;


import com.ccsw.tutorial.miam.cases.customer.model.Customer;
import com.ccsw.tutorial.miam.cases.customer.model.CustomerDto;

import java.util.List;

/**
 * @author miam
 */
public interface CustomerService {

    /**
     * Método para recuperar todos los {@link Customer}
     *
     * @return {@link List} de {@link Customer}
     */
    List<Customer> findAll();

    /**
     * Recupera un {@link Customer} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Customer}
     */
    Customer get(Long id);

    /**
     * Método para crear o actualizar un {@link Customer} si no existe su nombre.
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, CustomerDto dto) throws Exception;


    /**
     * Método para borrar un {@link Customer}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}


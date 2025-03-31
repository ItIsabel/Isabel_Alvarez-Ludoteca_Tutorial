package com.ccsw.tutorial.miam.customer;


import com.ccsw.tutorial.miam.entities.customer.CustomerRepository;
import com.ccsw.tutorial.miam.entities.customer.model.Customer;
import com.ccsw.tutorial.miam.entities.customer.model.CustomerDto;
import com.ccsw.tutorial.miam.entities.customer.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan(basePackages = "com.ccsw.tutorial.miam")
public class CustomerTest {
    public static final String CUSTOMER_NAME = "CAT1";
    public static final Long EXISTS_CUSTOMER_ID = 1L;
    public static final Long NOT_EXISTS_CUSTOMER_ID = 0L;
    public static final String EXIST_CUSTOMER_NAME = "Isabel Alvarez";

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    //Pruebas de listado
    @Test
    public void findAllShouldReturnAllCustomers() {

        List<Customer> list = new ArrayList<>();
        list.add(mock(Customer.class));

        when(customerRepository.findAll()).thenReturn(list);

        List<Customer> customers = customerService.findAll();

        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    //Pruebas de creación
    @Test
    public void saveNotExistsCustomerIdShouldInsert() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(CUSTOMER_NAME);

        ArgumentCaptor<Customer> customer = ArgumentCaptor.forClass(Customer.class);

        customerService.save(null, customerDto);

        verify(customerRepository).save(customer.capture());

        assertEquals(CUSTOMER_NAME, customer.getValue().getName());
    }

    @Test
    public void saveExistsCustomerNameShouldNotInsert() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(EXIST_CUSTOMER_NAME);
        List<Customer> list = new ArrayList<>();
        list.add(mock(Customer.class));

        when(customerRepository.findByNameIgnoreCase(EXIST_CUSTOMER_NAME)).thenReturn(list);
        assertThrows(Exception.class, () -> {
            customerService.save(null, customerDto);
        });
    }

    //Pruebas de modificación
    @Test
    public void saveExistsCustomerIdShouldUpdate() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(CUSTOMER_NAME);

        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        customerService.save(EXISTS_CUSTOMER_ID, customerDto);

        verify(customerRepository).save(customer);
    }

    //Pruebas de borrado
    @Test
    public void deleteExistsCustomerIdShouldDelete() throws Exception {

        Customer customer = mock(Customer.class);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        customerService.delete(EXISTS_CUSTOMER_ID);

        verify(customerRepository).deleteById(EXISTS_CUSTOMER_ID);
    }


    @Test
    public void getExistsCustomerIdShouldReturnCustomer() {

        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(EXISTS_CUSTOMER_ID);
        when(customerRepository.findById(EXISTS_CUSTOMER_ID)).thenReturn(Optional.of(customer));

        Customer customerResponse = customerService.get(EXISTS_CUSTOMER_ID);

        assertNotNull(customerResponse);
        assertEquals(EXISTS_CUSTOMER_ID, customer.getId());
    }

    @Test
    public void getNotExistsCustomerIdShouldReturnNull() {

        when(customerRepository.findById(NOT_EXISTS_CUSTOMER_ID)).thenReturn(Optional.empty());

        Customer customer = customerService.get(NOT_EXISTS_CUSTOMER_ID);

        assertNull(customer);
    }
}



package com.example.microservice;

import com.example.microservice.Entities.Customer;
import com.example.microservice.Repositories.CustomerRepository;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

public class CustomerServiceStepDef {
    @Mock
    private CustomerRepository mockCustomerRepository;
    private Customer expectedCustomer = new Customer();

    public CustomerServiceStepDef() {
        mockCustomerRepository = mock(CustomerRepository.class);
    }






}

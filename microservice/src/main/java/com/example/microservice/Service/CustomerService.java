package com.example.microservice.Service;

import com.example.microservice.Entities.Customer;
import com.example.microservice.KafkaProducer;
import com.example.microservice.Repositories.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper, KafkaProducer kafkaProducer) {
        this.customerRepository = customerRepository;
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "customers", key = "#id")
    public Customer getCustomerById(String id) {
     //   log.info("Fetching customer from database for ID: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "no customer"));
    }

    public Customer addCustomer( Customer customer) throws JsonProcessingException {
        Customer  returned = customerRepository.save(customer);
        // kafka here
        String customerJson = objectMapper.writeValueAsString(returned);
        kafkaProducer.sendMessage("customer-topic", customerJson);
        return returned;
    }
    //catch (JsonProcessingException e) {
        //throw new RuntimeException("Error processing JSON", e);
   // }

    @CachePut(value = "customers", key = "#customer.id")
    public Customer updateCustomer( Customer input)
    {
        try {
        Customer updatedCustomer = customerRepository.save(input);
        return updatedCustomer;
        }
        catch (Exception e) {
            throw  new RuntimeException("Failed to update customer", e);
        }
    }

    @CacheEvict(value = "customers", key = "#id")
    public boolean deleteVehicleById(String id) {
        try {
        customerRepository.deleteById(id);
        return true;
        }
        catch (Exception e) {
        return false;
        }

    }


    }

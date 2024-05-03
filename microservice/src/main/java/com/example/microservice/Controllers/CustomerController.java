package com.example.microservice.Controllers;

import com.example.microservice.Controllers.Dtos.CustomerDto;
import com.example.microservice.Entities.Customer;
import com.example.microservice.KafkaProducer;
import com.example.microservice.Repositories.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class CustomerController {

    CustomerRepository customerRepository;
     KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Autowired
            public CustomerController(CustomerRepository customerRepository,ObjectMapper objectMapper, KafkaProducer kafkaProducer) {
                this.customerRepository = customerRepository;
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable("id")String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "no customer"));
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws JsonProcessingException {
       Customer  returned = customerRepository.save(customer);
       // kafka here
        String customerJson = objectMapper.writeValueAsString(customer);
        kafkaProducer.sendMessage("customer-topic", customerJson);
        return ResponseEntity.ok(returned);
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id")String id, @RequestBody CustomerDto input)
    {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        customer.setFirstName(input.getFirstName());
        customer.setLastName(input.getLastName());
        Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }
    @DeleteMapping("/vehicle/remove/{id}")
    public boolean deleteVehicleById(@PathVariable("id")String id)
    {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

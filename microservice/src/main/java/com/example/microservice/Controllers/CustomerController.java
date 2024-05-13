package com.example.microservice.Controllers;

import com.example.microservice.Controllers.Dtos.CustomerDto;
import com.example.microservice.Entities.Customer;
import com.example.microservice.Service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final ObjectMapper objectMapper;
    CustomerService customerService;

    @Autowired
            public CustomerController(ObjectMapper objectMapper, CustomerService customerService) {
        this.objectMapper = objectMapper;
        this.customerService = customerService;
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable("id")String id) {
        return customerService.getCustomerById(id);
            //    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "no customer"));
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws JsonProcessingException {
      Customer returnedCustomer =  customerService.addCustomer(customer);
      return ResponseEntity.ok(returnedCustomer);
    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id")String id, @RequestBody CustomerDto input)
    {
        Customer customer = customerService.getCustomerById(id);
        customer.setFirstName(input.getFirstName());
        customer.setLastName(input.getLastName());

        // service update here
       Customer updatedCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updatedCustomer);
    }
    @DeleteMapping("/vehicle/remove/{id}")
    public boolean deleteVehicleById(@PathVariable("id")String id)
    {
       boolean success = customerService.deleteVehicleById(id);
       if (success)
       {return true;}
       else
       {return false;}
    }

}

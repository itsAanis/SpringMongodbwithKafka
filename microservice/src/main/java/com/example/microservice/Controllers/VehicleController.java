package com.example.microservice.Controllers;

import com.example.microservice.Entities.Vehicle;
import com.example.microservice.Repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
public class VehicleController {
VehicleRepository vehicleRepository;
    @Autowired
    public VehicleController(VehicleRepository vehicleRepository) {

        this.vehicleRepository = vehicleRepository;
        }

    @GetMapping("/vehicle/{id}")
    public Vehicle getVehicleById(@PathVariable("id")String id) {
        return vehicleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "no vehicle"));
        }


    @PostMapping("/vehicle")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
    Vehicle  returned = vehicleRepository.save(vehicle);
        return ResponseEntity.ok(returned);

    }



}

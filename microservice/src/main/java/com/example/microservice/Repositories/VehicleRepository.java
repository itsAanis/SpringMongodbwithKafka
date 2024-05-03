package com.example.microservice.Repositories;

import com.example.microservice.Entities.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {


}

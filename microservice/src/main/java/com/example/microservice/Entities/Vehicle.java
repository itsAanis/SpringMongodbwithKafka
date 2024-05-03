package com.example.microservice.Entities;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    String id;
    String name;
    String fuelType;
    String engineSize;

}

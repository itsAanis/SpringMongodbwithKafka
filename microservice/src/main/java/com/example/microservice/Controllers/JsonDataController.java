package com.example.microservice.Controllers;

import com.example.microservice.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JsonDataController {

    private DataService dataService;
    @Autowired
    public JsonDataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/process-json")
    public ResponseEntity<Object> processJsonData() {
        try {
            Object result = dataService.evaluateJsonata();
            return ResponseEntity.ok(result); // Directly return the result
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON data");
        }
    }

}

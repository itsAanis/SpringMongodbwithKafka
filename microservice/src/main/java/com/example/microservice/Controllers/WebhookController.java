package com.example.microservice.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebhookController {

    @PostMapping("/webhook")
    public void receiveWebhook(@RequestBody String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Payload parsedPayload = objectMapper.readValue(payload, Payload.class);
     //   log.info("Received webhook with event type: {} and payload: {}", parsedPayload.getEventType(), parsedPayload);
    }
}

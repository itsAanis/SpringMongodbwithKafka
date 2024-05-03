package com.example.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaListenerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    @KafkaListener(topics = "customer-topic", groupId = "customer-group")
    public void listenToCustomerEvents(String customer) {
        logger.info("Received customer event: {}", customer);
    }
}

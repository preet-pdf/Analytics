package com.example.demo.resource;

import com.example.demo.DTO.AuditEvent;
import com.example.demo.service.AuditAlert;
import com.example.demo.service.AuditDataProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderMetaData {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMetaData.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuditDataProcess auditDataProcess;

    @Autowired
    AuditAlert auditAlert;

    @KafkaListener(topics = "ordersss", groupId = "baristas")
    public void process(com.example.webdevelopment.DTO.OrderDetails order) {
        LOGGER.info(order.toString());
    }

    @KafkaListener(topics = "audit_event", groupId = "audit_event")
    public void auditConsumer(String auditEventJson) {
        try {
            AuditEvent auditEvent = objectMapper.readValue(auditEventJson, AuditEvent.class);
            // Here you have the deserialized AuditEvent object
            // You can now process it as needed
            LOGGER.info("Received audit event: " + auditEvent);
            auditAlert.createAlert(auditEvent);
            auditDataProcess.processAuditEvent(auditEvent);
            auditDataProcess.printEventTypeCountsPerHour();

        } catch (Exception e) {
            LOGGER.error("Error deserializing audit event: " + e.getMessage());
        }
    }

}

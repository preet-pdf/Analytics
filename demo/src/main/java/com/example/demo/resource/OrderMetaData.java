package com.example.demo.resource;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import com.example.demo.service.AuditAlert;
import com.example.demo.service.AuditDataProcess;
import com.example.demo.service.ConfigurationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderMetaData {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMetaData.class);

    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplate;

    public void sendAlertData(AlertDTO alertDTO) throws JsonProcessingException {
        String alertDataDTO = objectMapper.writeValueAsString(alertDTO);
        kafkaTemplate.send("alert", alertDataDTO);
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuditDataProcess auditDataProcess;

    @Autowired
    AuditAlert auditAlert;

    @Autowired
    ConfigurationService configurationService;

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
            auditDataProcess.processAuditEvent(auditEvent);
            System.out.println(configurationService.getRuleStatus());

            if (configurationService.getRuleStatus()) {
                AlertDTO alertDTO = auditAlert.createAlert(auditEvent);
                System.out.println(alertDTO);
                if (alertDTO != null) {
                    sendAlertData(alertDTO);
                }
            }
            auditDataProcess.printEventTypeCountsPerHour();
        } catch (Exception e) {
            LOGGER.error("Error deserializing audit event: " + e.getMessage());
        }
    }

}

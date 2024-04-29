package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AuditAlert {

    @Autowired
    private ValidationRules rulesConfig;

    @Autowired
    AuditDataProcess auditDataProcess;

    @Autowired
    ObjectMapper objectMapper;

    public void createAlert(AuditEvent event) throws JsonProcessingException {
        AuditEvents auditEvent = event.getAuditEvent();
        System.out.println(auditEvent.equals(AuditEvents.BUTTON));
        if (auditEvent.equals(AuditEvents.BUTTON)) {
            processButtonEvent (event);
        } else if (auditEvent.equals(AuditEvents.INPUT)) {
            processInputEvent(event);
        } else if (auditEvent.equals(AuditEvents.SESSION)) {
            processSessionEvent(event);
        }
    }

    private void processSessionEvent(AuditEvent event) {

    }

    private void processInputEvent(AuditEvent event) {
    }

    private void processButtonEvent(AuditEvent event) throws JsonProcessingException {
        Map<String, String> rules = getRules();
        System.out.println(rules.get("Button"));
//        LinkedHashMap button = objectMapper.readValue(rules.get("Button"), LinkedHashMap.class);
//        System.out.println(button);
    }

    private Map<String, String> getRules() {
        return rulesConfig.getRules().get("rules");
    }
}

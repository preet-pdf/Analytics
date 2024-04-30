package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuditAlert {

    @Autowired
    private ValidationRules rulesConfig;

    @Autowired
    AuditDataProcess auditDataProcess;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ButtonAlert buttonAlert;

    @Autowired
    TextAlert textAlert;

    @Autowired
    SessionAlert sessionAlert;

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
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Screen");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                sessionAlert.checkAlert(event, entry);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processInputEvent(AuditEvent event) {
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Text");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                textAlert.checkAlert(event, entry);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processButtonEvent(AuditEvent event) throws JsonProcessingException {
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Button");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                buttonAlert.checkAlert(event, entry);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Map<String, Map<String, String>> getRules() {
        return rulesConfig.getRules().get("rules");
    }
}

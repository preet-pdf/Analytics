package com.example.demo.service;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import com.example.demo.Enum.AuditEvents;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public static ArrayList<AlertDTO> alertList = new ArrayList<>();

    public AlertDTO createAlert(AuditEvent event) throws JsonProcessingException {
        AuditEvents auditEvent = event.getAuditEvent();
        System.out.println(auditEvent.equals(AuditEvents.BUTTON));
        if (auditEvent.equals(AuditEvents.BUTTON)) {
            return processButtonEvent(event);
        } else if (auditEvent.equals(AuditEvents.INPUT)) {
            return processInputEvent(event);
        } else if (auditEvent.equals(AuditEvents.SESSION)) {
            return processSessionEvent(event);
        }
        System.out.println(alertList);
        return null;
    }

    private AlertDTO processSessionEvent(AuditEvent event) {
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Screen");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                AlertDTO alert = sessionAlert.checkAlert(event, entry);
                if (alert.getRuleName() != null) {
                    return alert;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private AlertDTO processInputEvent(AuditEvent event) {
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Text");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                AlertDTO alert = textAlert.checkAlert(event, entry);
                if (alert.getRuleName() != null) {
                    return alert;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private AlertDTO processButtonEvent(AuditEvent event) throws JsonProcessingException {
        Map<String, Map<String, String>> rules = getRules();
        try {
            Map<String, String> buttonRules = rules.get("Button");
            for (Map.Entry<String, String> entry : buttonRules.entrySet()) {
                AlertDTO alert = buttonAlert.checkAlert(event, entry);
                if (alert.getRuleName() != null) {
                    return alert;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private Map<String, Map<String, String>> getRules() {
        return rulesConfig.getRules().get("rules");
    }
}

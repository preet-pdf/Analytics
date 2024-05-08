package com.example.demo.service;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import com.example.demo.utils.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SessionAlert {

    @Autowired
    GetTime getTime;

    public boolean toMuchScreenTime(AuditEvent event) {
        /*should not have screen time of more than 10 mins*/
        return Integer.parseInt(event.getEventData()) >= 600;
    }

    public AlertDTO checkAlert(AuditEvent event, Map.Entry<String, String> entry) {
        String RuleName = entry.getKey();
        String RuleDesc = entry.getValue();
        long currentEpochTime = getTime.getCurrentEpochTime();
        if (toMuchScreenTime(event)) {
            return AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();
        }
        return AlertDTO.builder().build();
    }
}

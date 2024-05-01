package com.example.demo.service;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ButtonAlert {

    @Autowired
    AuditDataProcess auditDataProcess;

    public boolean inValidButton(AuditEvent event) {
        if (event.getEventType().equals("invalid_button")) return true;
        return false;
    }

    public boolean shouldNotBeRedButton(AuditEvent event) {
        if (event.getEventType().equals("button_double_clicked")) return true;
        return false;
    }

    public boolean shouldNotClickedTooMuch(AuditEvent event) {
        Map<String, List<Long>> buttonClickedTime = AuditDataProcess.buttonClickedTime;
        List<Long> buttonClickedTimeList = buttonClickedTime.get(event.getEventData());
        if (buttonClickedTimeList.size() <= AuditDataProcess.MAX_CLICKED) {
            return false;
        } else return (buttonClickedTimeList.get(AuditDataProcess.MAX_CLICKED - 1) - buttonClickedTimeList.get(0)) <= 10;
    }

    private long getCurrentEpochTime() {
        Instant now = Instant.now();
        return now.toEpochMilli();
    }

    public List<AlertDTO> checkAlert(AuditEvent event, Map.Entry<String, String> entry) {
        String RuleName = entry.getKey();
        String RuleDesc = entry.getValue();
        List<AlertDTO> buttonAlertList = new ArrayList<>();

        if (RuleName.equals("invalidButton") && inValidButton(event)) {
            long currentEpochTime = getCurrentEpochTime();
            AlertDTO alertDTO = AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();
            buttonAlertList.add(alertDTO);
        } else if (RuleName.equals("redButton") && shouldNotBeRedButton(event)) {
            long currentEpochTime = getCurrentEpochTime();

        } else if (RuleName.equals("toManyClicks") && shouldNotClickedTooMuch(event)) {
            long currentEpochTime = getCurrentEpochTime();

        }

        return buttonAlertList;
    }
}

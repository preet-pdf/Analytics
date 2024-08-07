package com.example.demo.service;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import com.example.demo.utils.GetTime;
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

    @Autowired
    GetTime getTime;

    public boolean inValidButton(AuditEvent event) {
        if ((event.getEventType().equals("alert_toggle") || event.getEventType().equals("rule_toggle")) && event.getEventData().equals("false")) return true;
        return false;
    }

    public boolean shouldNotBeRedButton(AuditEvent event) {
        if (event.getEventType().equals("signin_button_click") || event.getEventType().equals("logout_button_click")) return true;
        return false;
    }

    public boolean shouldNotClickedTooMuch(AuditEvent event) {
        if (event.getEventType().equals("create_user_button_click")
                || event.getEventType().equals("create_role_and_assign_permission")
                || event.getEventType().equals("create_role")
                || event.getEventType().equals("assign_permission")) {
            Map<String, List<Long>> buttonClickedTime = AuditDataProcess.buttonClickedTime;
            List<Long> buttonClickedTimeList = buttonClickedTime.get(event.getEventData());
            System.out.println(buttonClickedTimeList);
            System.out.println((buttonClickedTimeList.get(AuditDataProcess.MAX_CLICKED - 1) - buttonClickedTimeList.get(0)));
            if (buttonClickedTimeList.size() < AuditDataProcess.MAX_CLICKED) {
                return false;
            } else
                return (buttonClickedTimeList.get(0) - buttonClickedTimeList.get(AuditDataProcess.MAX_CLICKED - 1)) <= 10;
        } else {
            return false;
        }
    }

    public AlertDTO checkAlert(AuditEvent event, Map.Entry<String, String> entry) {
        String RuleName = entry.getKey();
        String RuleDesc = entry.getValue();

        if (RuleName.equals("invalidButton") && inValidButton(event)) {
            System.out.println("invalidButtom");
            long currentEpochTime = getTime.getCurrentEpochTime();
            return AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();

        } else if (RuleName.equals("redButton") && shouldNotBeRedButton(event)) {
            System.out.println("redButton");
            long currentEpochTime = getTime.getCurrentEpochTime();
            return AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();

        } else if (RuleName.equals("toManyClicks") && shouldNotClickedTooMuch(event)) {
            System.out.println("Here");
            long currentEpochTime = getTime.getCurrentEpochTime();
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

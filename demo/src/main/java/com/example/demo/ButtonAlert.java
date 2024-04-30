package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ButtonAlert {

    public boolean inValidButton(AuditEvent event) {

    }

    public boolean shouldNotBeRedButton(AuditEvent event) {

    }

    public boolean shouldNotClickedTooMuch(AuditEvent event) {

    }

    public void checkAlert(AuditEvent event, Map.Entry<String, String> entry) {
        String RuleName = entry.getKey();
        String RuleDesc = entry.getValue();


        if (RuleName.equals("invalidButton") && inValidButton(event)) {

        } else if (RuleName.equals("redButton") && shouldNotBeRedButton(event)) {

        } else if (RuleName.equals("toManyClicks") && shouldNotClickedTooMuch(event)) {

        }
    }
}

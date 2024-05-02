package com.example.demo.service;

import com.example.demo.DTO.AuditEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SessionAlert {

    public void toMuchScreenTime(AuditEvent event) {
        /*should not have screen time of more than 10 mins*/
    }

    public void checkAlert(AuditEvent event, Map.Entry<String, String> entry) {

    }
}

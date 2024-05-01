package com.example.demo.DTO;

import com.example.demo.Enum.AuditEvents;
import lombok.Data;

import java.util.Date;

@Data
public class AuditEvent {

    private String eventType;
    private String eventData;
    private Date eventTime;
    private AuditEvents auditEvent;
}

package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AlertDTO {
    private AuditEvent auditEvent;
    private String ruleName;
    private String ruleDescription;
    private long createdTime;
}

package com.example.demo.service;

public interface ConfigurationService {
    Boolean getRuleStatus();
    void setRuleStatus(Boolean value);

    Boolean getAlertStatus();
    void setAlertStatus(Boolean value);
}

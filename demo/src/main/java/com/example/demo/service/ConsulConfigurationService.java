package com.example.demo.service;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.springframework.stereotype.Service;

@Service
public class ConsulConfigurationService implements ConfigurationService {

    private final ConsulClient consulClient;

    public ConsulConfigurationService() {
        this.consulClient = new ConsulClient("localhost");
    }

    @Override
    public Boolean getRuleStatus() {
        return Boolean.valueOf(getValueFromConsul("RuleStatus"));
    }

    @Override
    public void setRuleStatus(Boolean value) {
        setValueInConsul("RuleStatus", String.valueOf(value));
    }

    @Override
    public Boolean getAlertStatus() {
        return Boolean.valueOf(getValueFromConsul("AlertStatus"));
    }

    @Override
    public void setAlertStatus(Boolean value) {
        setValueInConsul("AlertStatus", String.valueOf(value));
    }

    private String getValueFromConsul(String key) {
        GetValue value = consulClient.getKVValue(key).getValue();
        return value != null ? value.getDecodedValue() : null;
    }

    private void setValueInConsul(String key, String value) {
        consulClient.setKVValue(key, value);
    }
}

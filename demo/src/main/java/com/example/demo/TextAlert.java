package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextAlert {

    public void invalidText(AuditEvent event) {

    }

    public void shouldNotContainBadWords(AuditEvent event) {

    }

    public void shouldNotContainSoManySpecialChars(AuditEvent event) {

    }

    public void checkAlert(AuditEvent event, Map.Entry<String, String> entry) {

    }
}

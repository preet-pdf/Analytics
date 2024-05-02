package com.example.demo.service;

import com.example.demo.DTO.AuditEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextAlert {

    public void invalidText(AuditEvent event) {
        /*not chars or only digits or more than 300 chars*/
    }

    public void shouldNotContainBadWords(AuditEvent event) {
        /*check bad words in string*/
    }

    public void shouldNotContainSoManySpecialChars(AuditEvent event) {
        /*it should not have more then 5 special chars or emojis*/
    }

    public void checkAlert(AuditEvent event, Map.Entry<String, String> entry) {

    }
}

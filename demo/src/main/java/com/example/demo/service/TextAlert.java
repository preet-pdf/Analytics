package com.example.demo.service;

import com.example.demo.DTO.AlertDTO;
import com.example.demo.DTO.AuditEvent;
import com.example.demo.utils.GetTime;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TextAlert {

    @Autowired
    GetTime getTime;

    private static Set<String> badWordsSet;

    static {
        badWordsSet = loadBadWordsFromResource();
    }

    @PostConstruct
    private static Set<String> loadBadWordsFromResource() {
        Set<String> badWords = new HashSet<>();
        try {
            ClassPathResource resource = new ClassPathResource("/Users/preetparikh/Downloads/Analytics/demo/src/main/resources/badWords");
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim().toLowerCase());
            }

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return badWords;
    }



    public boolean invalidText(AuditEvent event) {
        /*not chars or only digits or more than 300 chars*/
        return event.getEventData().length() >= 300;
    }

    public boolean shouldNotContainBadWords(AuditEvent event) {
        /*check bad words in string*/
        String[] words = event.getEventData().split("[^\\w]+");
        System.out.println(badWordsSet);

        for (String word : words) {
            if (badWordsSet.contains(word)) {
                return true;
            }
        }
    }

    public boolean shouldNotContainSoManySpecialChars(AuditEvent event) {
        /*it should not have more than 5 special chars or emojis*/
        Pattern specialCharsPattern = Pattern.compile("[!@#$%&*()_+=|^<>?{}\\[\\]~-]");
        Matcher matcher = specialCharsPattern.matcher(event.getEventData());
        int specialCharCount = 0;
        while (matcher.find()) {
            specialCharCount++;
        }
        return specialCharCount > 5;

    }

    public AlertDTO checkAlert(AuditEvent event, Map.Entry<String, String> entry) {
        String RuleName = entry.getKey();
        String RuleDesc = entry.getValue();
        if (RuleName.equals("invalidText") && invalidText(event)) {
            System.out.println("invalidText");
            long currentEpochTime = getTime.getCurrentEpochTime();
            return AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();
        } else if (RuleName.equals("badWord") && shouldNotContainBadWords(event)) {
            System.out.println("badWord");
            long currentEpochTime = getTime.getCurrentEpochTime();
            return AlertDTO.builder()
                    .auditEvent(event)
                    .ruleName(RuleName)
                    .ruleDescription(RuleDesc)
                    .createdTime(currentEpochTime)
                    .build();
        } else if(RuleName.equals("manySpecialChars") && shouldNotContainSoManySpecialChars(event)) {
            System.out.println("manySpecialChars");
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

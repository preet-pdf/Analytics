package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Getter
@Component
public class ValidationRules {


    private Map<String, Map<String, Map<String, String>>> rules;

    @PostConstruct
    public void loadRules() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("rules.json");
        rules = mapper.readValue(resource.getInputStream(), Map.class);
    }

}

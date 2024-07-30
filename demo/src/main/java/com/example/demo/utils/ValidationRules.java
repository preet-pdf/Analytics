package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Getter
@Component
public class ValidationRules {


    private Map<String, Map<String, Map<String, String>>> rules;
    private Map<String, Map<String, Boolean>> rulesOnly;
    private File fileResource;
//    private String writeRules;

    @PostConstruct
    public Map<String, Map<String, Map<String, String>>> loadRules() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("rules.json");
        fileResource = resource.getFile();
        System.out.println(fileResource);
        rules = mapper.readValue(resource.getInputStream(), Map.class);
        rulesOnly = mapper.readValue(resource.getInputStream(), Map.class);
        System.out.println(rules);
        return rules;
//        writeRules = mapper.writeValueAsString(resource.getInputStream());
    }

}

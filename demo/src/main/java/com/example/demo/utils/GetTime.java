package com.example.demo.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class GetTime {
    public long getCurrentEpochTime() {
        Instant now = Instant.now();
        return now.toEpochMilli();
    }
}

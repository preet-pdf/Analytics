package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetHelloWorld {


    @RequestMapping(value = "/ex/foos", method = RequestMethod.GET)
    public String getHelloWorld() {
        return "Hellow World";
    }
}

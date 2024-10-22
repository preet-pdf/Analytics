package com.example.demo.service;

import com.example.demo.utils.ValidationRules;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
@GrpcService
public class SendRules extends GetRuleServiceGrpc.GetRuleServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendRules.class);

    @Autowired
    private ValidationRules rulesConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public void getRule(com.org.grpc.Void request, StreamObserver<GetRuleResponse> responseObserver) {
        try {
            // Assuming you load rules from a configuration or file
            Map<String, Map<String, Map<String, String>>> rules = rulesConfig.loadRules(); // Fetching rules from service
            String rulesJson = objectMapper.writeValueAsString(rules);  // Convert rules to JSON format

            // Logging the rules
            LOGGER.info("Fetched rules: {}", rulesJson);

            // Building the response
            GetRuleResponse response = GetRuleResponse.newBuilder()
                    .setResponse(rulesJson)
                    .build();

            // Sending the response to the client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IOException e) {
            LOGGER.error("Error fetching or parsing rules", e);
            responseObserver.onError(e);
        }
    }
}

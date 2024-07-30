package com.example.demo.service;

import com.example.demo.utils.ValidationRules;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.grpc.UpdateRuleRequest;
import com.org.grpc.UpdateRuleResponse;
import com.org.grpc.UpdateRuleServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@GrpcService
public class UpdateRuleServiceImpl extends UpdateRuleServiceGrpc.UpdateRuleServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRuleServiceImpl.class);

    @Autowired
    ValidationRules rulesConfig;

    @Autowired
    ObjectMapper objectMapper;

    private Map<String, Map<String, Boolean>> getRules() {
        return rulesConfig.getRulesOnly();
    }

    @Override
    public StreamObserver<UpdateRuleRequest> updateRule(StreamObserver<UpdateRuleResponse> responseObserver) {

        return new StreamObserver<UpdateRuleRequest>() {
            @Override
            public void onNext(UpdateRuleRequest request) {
                LOGGER.info("Request Received: " + request.getStatus());
                Map<String, Map<String, Boolean>> rules = null;
                System.out.println(rulesConfig.getRules());
                rules = getRules();
                rules.get("StopRule").put("StopAllRule", (request.getStatus()));
                System.out.println(rules.get("StopRule").get("StopAllRule"));
                try {
                    File file = rulesConfig.getFileResource();
                    System.out.println(file);
                    objectMapper.writeValue(file, rules);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Handle the incoming request and send a response
                UpdateRuleResponse response = UpdateRuleResponse.newBuilder()
                        .setResponse("Status received: " + request.getStatus())
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                // Handle error
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                // Complete the response
                responseObserver.onCompleted();
            }
        };
    }
}

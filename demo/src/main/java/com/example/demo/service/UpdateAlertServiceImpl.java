package com.example.demo.service;

import com.example.demo.resource.OrderMetaData;
import com.example.demo.utils.ValidationRules;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

@GrpcService
public class UpdateAlertServiceImpl extends UpdateAlertStatusServiceGrpc.UpdateAlertStatusServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRuleServiceImpl.class);
    @Autowired
    private ValidationRules rulesConfig;
    @Autowired
    ObjectMapper objectMapper;


    @Override
    public StreamObserver<AlertRequest> updateAlertStatus(StreamObserver<AlertResponse> responseObserver) {
        return new StreamObserver<AlertRequest>() {

            @Override
            public void onNext(AlertRequest alertRequest) {
                LOGGER.info("Alert Request Received: " + alertRequest.getStatus());
                AlertResponse response = AlertResponse.newBuilder()
                        .setResponse("Status received: " + alertRequest.getStatus())
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

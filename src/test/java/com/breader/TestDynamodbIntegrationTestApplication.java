package com.breader;

import com.breader.infrastructure.DynamodbIntegrationTestApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestDynamodbIntegrationTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(DynamodbIntegrationTestApplication::main).with(TestDynamodbIntegrationTestApplication.class).run(args);
    }

}

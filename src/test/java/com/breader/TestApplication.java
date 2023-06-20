package com.breader;

import org.springframework.boot.SpringApplication;

public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(AwsServicesTestConfig.class).run(args);
    }
}

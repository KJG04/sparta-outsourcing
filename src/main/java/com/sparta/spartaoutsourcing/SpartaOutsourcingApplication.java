package com.sparta.spartaoutsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpartaOutsourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaOutsourcingApplication.class, args);
    }

}

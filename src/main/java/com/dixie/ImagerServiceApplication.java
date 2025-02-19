package com.dixie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ImagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImagerServiceApplication.class, args);
    }

}

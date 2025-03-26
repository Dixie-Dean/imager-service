package com.dixie.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImagerServiceConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}

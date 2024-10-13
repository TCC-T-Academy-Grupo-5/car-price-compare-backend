package com.tcc5.car_price_compare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GeneralConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}

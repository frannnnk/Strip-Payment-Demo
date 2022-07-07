package com.frank.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)  {


        return restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(60000))
                .setConnectTimeout(Duration.ofMillis(60000))
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

    }

}
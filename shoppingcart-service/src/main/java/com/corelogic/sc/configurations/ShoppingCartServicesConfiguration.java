package com.corelogic.sc.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
public class ShoppingCartServicesConfiguration {

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean(name = "clock")
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

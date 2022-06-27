package com.vmware.tanzulabs.app.adapter.out.resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties( OrderResourceConfigurationProperties.class )
public class OrderResourceConfiguration {

    @Bean
    RestTemplate restTemplate() {

        return new RestTemplate();
    }

}

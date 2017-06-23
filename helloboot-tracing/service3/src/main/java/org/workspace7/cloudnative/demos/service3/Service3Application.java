package org.workspace7.cloudnative.demos.service3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kameshs
 */
@SpringBootApplication
public class Service3Application {

    public static void main(String[] args) {
        SpringApplication.run(Service3Application.class, args);
    }

    @Configuration
    public class Service3Config

    {
        @Bean
        public RestTemplate alwaysSampler() {
            return new RestTemplate();
        }
    }

}

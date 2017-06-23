package org.workspace7.cloudnative.demos.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kameshs
 */
@SpringBootApplication
public class Service2Application {

    public static void main(String[] args) {
        SpringApplication.run(Service2Application.class, args);
    }


    @Configuration
    public class Service2Config {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}

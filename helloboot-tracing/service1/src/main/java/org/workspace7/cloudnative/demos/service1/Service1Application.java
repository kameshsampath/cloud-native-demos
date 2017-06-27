package org.workspace7.cloudnative.demos.service1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kameshs
 */
@SpringBootApplication
@EnableConfigurationProperties(IstioProperties.class)
@Slf4j
public class Service1Application {

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }


    @Configuration
    public class Service1Config {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

    }
}

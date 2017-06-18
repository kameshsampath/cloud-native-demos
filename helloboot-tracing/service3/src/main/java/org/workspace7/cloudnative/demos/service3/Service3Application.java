package org.workspace7.cloudnative.demos.service3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kameshs
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Service3Application {

    public static void main(String[] args) {
        SpringApplication.run(Service3Application.class, args);
    }

    @Configuration
    public class Service3Config

    {
        @Bean
        public AlwaysSampler alwaysSampler() {
            return new AlwaysSampler();
        }
    }

}

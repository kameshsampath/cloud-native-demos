package org.workspace7.springcloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @author kameshs
 */
@SpringBootApplication
@EnableZipkinStreamServer
public class ZipkinServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZipkinServerApplication.class, args);
    }

    @Configuration
    public class ZipkinServerConfiguration {

        //TODO: ZipkinAutoConfiguration fails without this bean ..
        @Bean
        public List<SpanAdjuster> spanAdjusters() {
            return Collections.emptyList();
        }
    }


}

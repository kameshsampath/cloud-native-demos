package org.workspace7.cloudnative.demos.service1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {

    private Tracer tracer;
    private RestTemplate restTemplate;

    @Autowired
    public HelloController(Tracer tracer, RestTemplate restTemplate) {

        this.tracer = tracer;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String whoami() {

        Span span = this.tracer.createSpan("service1_whoami");

        span.tag("method", "whoami");

        span.logEvent("Knowing myself");

        this.tracer.close(span);

        return String.format("Host %s", System.getenv().getOrDefault("HOST_NAME", "Unknown"));
    }

    @GetMapping("/hello")
    public String hello() {

        Span span = this.tracer.createSpan("service1_hello");

        span.tag("method", "hello");

        span.logEvent("Calling Service 2");

        String response = restTemplate.getForObject("http://service2/hello", String.class);

        span.logEvent("Received response from  Service 2");

        this.tracer.close(span);

        return response;
    }
}

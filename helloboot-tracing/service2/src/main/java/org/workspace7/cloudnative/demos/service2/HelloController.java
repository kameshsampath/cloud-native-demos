package org.workspace7.cloudnative.demos.service2;

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

    @GetMapping(value = "/hello")
    public String hello() {

        Span span = this.tracer.createSpan("service2_hello");

        span.tag("method", "hello");

        //Call Service 3
        span.logEvent("Calling Service 3");
        String resp3 = restTemplate.getForObject("http://service3/hello", String.class);
        span.logEvent("Got response  Service 3");
        log.info("Response from Service 3 : {}", resp3);

        //Call Service 4
        span.logEvent("Calling Service 4");
        String resp4 = restTemplate.getForObject("http://service4/hello", String.class);
        span.logEvent("Got response from Service 4");
        log.info("Response from Service 4 : {}", resp4);

        this.tracer.close(span);

        return String.format("Hello from Service 2: \n %s %s", resp3, resp4);
    }
}

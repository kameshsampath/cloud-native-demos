package org.workspace7.cloudnative.demos.service3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {


    private Tracer tracer;

    @Autowired
    public HelloController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/hello")
    public String hello() {

        Span span = tracer.createSpan("service3_hello");

        span.tag("method", "hello");

        String response = String.format("Hello from Service 3");

        this.tracer.close(span);

        return response;
    }
}

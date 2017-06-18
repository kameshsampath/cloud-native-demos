package org.workspace7.cloudnative.demos.service4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {

    Tracer tracer;

    @Autowired
    public HelloController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/hello")
    public String hello() {
        Span span = this.tracer.createSpan("service4_hello");

        span.tag("method", "hello");

        String response = String.format("Hello from Service 4");

        tracer.close(span);

        return response;
    }

    @GetMapping("/longhello")
    public String longhello() {
        Span span = this.tracer.createSpan("service4_longhello");

        span.tag("method", "long-hello");

        try {
            SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }

        String response = String.format("Hello after 10 seconds");

        tracer.close(span);

        return response;
    }
}

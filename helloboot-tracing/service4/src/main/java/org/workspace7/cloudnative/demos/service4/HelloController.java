package org.workspace7.cloudnative.demos.service4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/service4")
    public String hello() {
        return message();
    }

    @GetMapping("/longhello")
    public String longhello() {

        log.info("Starting Long Hello ...");

        try {
            SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }

        String response = String.format(" after 10 seconds");

        log.info("Long Hello Ending ...");

        return message() + response;
    }

    private String message() {

        return String.format("V2: Hello by Service 4 from %s ",
            System.getenv().getOrDefault("HOSTNAME", "Unknown"));
    }
}

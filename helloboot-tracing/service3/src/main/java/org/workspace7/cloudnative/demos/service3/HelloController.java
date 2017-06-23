package org.workspace7.cloudnative.demos.service3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {


    @GetMapping("/service3")
    public String hello() {
        return message();
    }

    private String message() {

        return String.format("Hello by Service 3 from %s ",
            System.getenv().getOrDefault("HOSTNAME", "Unknown"));
    }
}

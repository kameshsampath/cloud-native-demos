package org.workspace7.cloudnative.demos.service1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kameshs
 */
@RestController
@Slf4j
public class HelloController {

    private final RestTemplate restTemplate;

    private final String[] TRACING_HEADERS = {"x-request-id", "x-b3-traceid", "x-b3-spanid",
        "x-b3-sampled", "x-b3-flags", "x-ot-span-context"};

    @Autowired
    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/service1")
    public String hello(HttpServletRequest request) {

        HttpHeaders headers = addForwardHeaders(request);

        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://service2:8080/service2",
            HttpMethod.GET, httpEntity, String.class);

        return String.format(responseEntity.getBody() + " from Host: %s ",
            System.getenv().getOrDefault("HOSTNAME", "Unknown"));
    }

    /**
     *
     * @param request
     * @return
     */
    HttpHeaders addForwardHeaders(HttpServletRequest request) {

        log.info("Service 1:: Adding Forward Headers ");

        HttpHeaders httpHeaders = new HttpHeaders();

        for (int i = 0; i < TRACING_HEADERS.length; i++) {
            String headerName = TRACING_HEADERS[i];
            String headerValue = request.getHeader(headerName);
            if (headerValue != null) {
                httpHeaders.add(headerName, headerValue);
            }
        }

        log.info("Service 1:: Forward Headers {}", httpHeaders);

        return httpHeaders;
    }
}

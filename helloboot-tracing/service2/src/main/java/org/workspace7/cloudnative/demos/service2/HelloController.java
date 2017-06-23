package org.workspace7.cloudnative.demos.service2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    private RestTemplate restTemplate;

    private final String[] TRACING_HEADERS = {"x-request-id", "x-b3-traceid", "x-b3-spanid",
        "x-b3-sampled", "x-b3-flags", "x-ot-span-context"};

    @Autowired
    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/service2")
    public String hello(HttpServletRequest request) {

        final HttpHeaders headers = addForwardHeaders(request);

        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://service3:8080/service3",
            HttpMethod.GET, httpEntity, String.class);

        String resp3 = responseEntity.getBody();

        responseEntity = restTemplate.exchange("http://service4:8080/service4",
            HttpMethod.GET, httpEntity, String.class);

        String resp4 = responseEntity.getBody();

        return buildResponse(resp3, resp4);
    }

    HttpHeaders addForwardHeaders(HttpServletRequest request) {

        log.info("Service 2:: Adding Forward Headers ");

        HttpHeaders httpHeaders = new HttpHeaders();

        for (int i = 0; i < TRACING_HEADERS.length; i++) {
            String headerName = TRACING_HEADERS[i];
            String headerValue = request.getHeader(headerName);
            if (headerValue != null) {
                httpHeaders.add(headerName, headerValue);
            }
        }

        log.info("Service 2:: Forward Headers {}", httpHeaders);

        return httpHeaders;
    }


    String buildResponse(String resp3, String resp4) {

        HelloResponse helloResponse = new HelloResponse();

        helloResponse.setService3Response(resp3);

        helloResponse.setService4Response(resp4);

        String response = "";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response = objectMapper.writeValueAsString(helloResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }
}

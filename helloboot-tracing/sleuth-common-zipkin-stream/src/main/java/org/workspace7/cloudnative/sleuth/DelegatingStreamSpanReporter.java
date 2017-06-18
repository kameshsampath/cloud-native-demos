package org.workspace7.cloudnative.sleuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.stream.StreamSpanReporter;
import org.springframework.stereotype.Component;

/**
 * @author kameshs
 */
@Component
@Slf4j
public class DelegatingStreamSpanReporter implements SpanReporter {

    private final StreamSpanReporter delegate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public DelegatingStreamSpanReporter(StreamSpanReporter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void report(Span span) {

        // skip all health checks and other requests being sent to zipkin
        if (!"http:/health".equals(span.getName())) {
            try {
                log.trace("Sending span [" + objectMapper.writeValueAsString(span) + "] to Zipkin");
            } catch (JsonProcessingException e) {
                log.error("Error sending span {}", e);
            }
            this.delegate.report(span);
        }
    }
}

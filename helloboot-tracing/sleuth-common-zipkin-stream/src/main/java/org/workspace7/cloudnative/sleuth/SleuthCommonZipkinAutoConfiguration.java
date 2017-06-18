package org.workspace7.cloudnative.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClient;
import org.springframework.cloud.sleuth.SpanReporter;
import org.springframework.cloud.sleuth.stream.HostLocator;
import org.springframework.cloud.sleuth.stream.StreamSpanReporter;
import org.springframework.cloud.sleuth.stream.ZipkinProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author kameshs
 */
@Configuration
@Slf4j
public class SleuthCommonZipkinAutoConfiguration {
    @Bean
    @Primary
    public SpanReporter mySpanReporter(StreamSpanReporter streamSpanReporter) {
        log.info("Init MY span Reporter {}" + streamSpanReporter.toString());
        return new DelegatingStreamSpanReporter(streamSpanReporter);
    }

    @Bean
    public HostLocator podHostLocator(KubernetesDiscoveryClient discoveryClient, ZipkinProperties zipkinProperties) {
        return new PodHostLocator(discoveryClient, zipkinProperties);
    }

}

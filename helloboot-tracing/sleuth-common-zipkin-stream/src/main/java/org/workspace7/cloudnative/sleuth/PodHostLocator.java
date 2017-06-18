package org.workspace7.cloudnative.sleuth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryClient;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.stream.Host;
import org.springframework.cloud.sleuth.stream.HostLocator;
import org.springframework.cloud.sleuth.stream.ZipkinProperties;
import org.springframework.util.StringUtils;

/**
 * TODO name it nicely and revisit the logic
 *
 * @author kameshs
 */
@Slf4j
public class PodHostLocator implements HostLocator {

    private final KubernetesDiscoveryClient discoveryClient;
    private final ZipkinProperties zipkinProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PodHostLocator(KubernetesDiscoveryClient discoveryClient, ZipkinProperties zipkinProperties) {
        this.discoveryClient = discoveryClient;
        this.zipkinProperties = zipkinProperties;
    }

    @Override
    public Host locate(Span span) {
        try {
            log.info("Locating span [" + objectMapper.writeValueAsString(span) + "]");
            ServiceInstance serviceInstance = this.discoveryClient.getLocalServiceInstance();
            String serviceId = StringUtils.hasText(this.zipkinProperties.getService().getName()) ?
                this.zipkinProperties.getService().getName() : serviceInstance.getServiceId();
            Host host = new Host(serviceId, serviceInstance.getHost(), serviceInstance.getPort());
            log.info("SPAN HOST:{}", host);
            return host;
        } catch (JsonProcessingException e) {
            log.error("Error sending span {}", e);
        }
        return null;
    }
}

package org.nure.julia.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ServiceDescriptionUpdater {

    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";
    private static final String KEY_SWAGGER_URL = "swagger_url";

    private final DiscoveryClient discoveryClient;
    private final ServiceDefinitionsContext definitionContext;
    private final RestTemplate template;

    @Value("${swagger.services.exclude}")
    private Set<String> excludeSwaggerConfig;

    @Autowired
    public ServiceDescriptionUpdater(DiscoveryClient discoveryClient, ServiceDefinitionsContext definitionContext) {
        this.template = new RestTemplate();
        this.discoveryClient = discoveryClient;
        this.definitionContext = definitionContext;
    }

    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerConfigurations() {
        discoveryClient.getServices().stream()
                .filter(serviceId -> !excludeSwaggerConfig.contains(serviceId))
                .forEach(serviceId -> {
                    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
                    if (serviceInstances != null && !serviceInstances.isEmpty()) {
                        ServiceInstance instance = serviceInstances.get(0);
                        String swaggerURL = getSwaggerURL(instance);
                        Optional<Object> jsonData = getSwaggerDefinitionForAPI(swaggerURL);
                        if (jsonData.isPresent()) {
                            String content = getJSON(jsonData.get());
                            definitionContext.addServiceDefinition(serviceId, content);
                        }
                    }
                });
    }

    private String getSwaggerURL(ServiceInstance instance) {
        String swaggerURL = instance.getMetadata().get(KEY_SWAGGER_URL);
        return swaggerURL != null ? instance.getUri() + swaggerURL : instance.getUri() + DEFAULT_SWAGGER_URL;
    }

    private Optional<Object> getSwaggerDefinitionForAPI(String url) {
        try {
            Object jsonData = template.getForObject(url, Object.class);
            return Optional.ofNullable(jsonData);
        } catch (RestClientException ex) {
            return Optional.empty();
        }
    }

    private String getJSON(Object jsonData) {
        try {
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}

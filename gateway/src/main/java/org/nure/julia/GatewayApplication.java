package org.nure.julia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/simple/**")
                        .uri("lb://SIMPLE-SERVICE")
                        .id("simple-service"))
                .route(r -> r.path("/api/auth/**")
                        .uri("lb://AUTHENTICATION-SERVICE")
                        .id("authentication-service"))
                .build();
    }

}

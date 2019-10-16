package org.nure.julia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/api/user/**")
                        .uri("lb://USER-SERVICE")
                        .id("user-service"))
                .route(r -> r.path("/authentication/api/authentication/**")
                        .uri("lb://AUTHENTICATION-SERVICE")
                        .id("authentication-service"))
                .route(r -> r.path("/device/api/device/**")
                        .uri("lb://DEVICE-SERVICE")
                        .id("device-service"))
                .build();
    }

}

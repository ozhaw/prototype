package org.nure.julia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
                .route(r -> r.path("/reports/api/reports/**")
                        .uri("lb://REPORTS-SERVICE")
                        .id("reports-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> route(HealthCheck healthCheck) {
        return RouterFunctions
                .route(RequestPredicates.GET("/actuator/info"), healthCheck::health)
                .andRoute(RequestPredicates.GET("/actuator/health"), healthCheck::health);
    }

}

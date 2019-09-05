package org.nure.julia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableHystrix
@EnableCircuitBreaker
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/simple/**")
                        .filters(f -> f.hystrix(c -> c.setName("default")
                                .setFallbackUri("forward:/fallback/error")))
                        .uri("lb://SIMPLE-SERVICE")
                        .id("simple-service"))
                .route(r -> r.path("/auth/**")
                        .filters(f -> f.hystrix(c -> c.setName("default")
                                .setFallbackUri("forward:/fallback/error")))
                        .uri("lb://AUTHENTICATION-SERVICE")
                        .id("authentication-service"))
                .build();
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just("fero");
    }

}

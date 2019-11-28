package org.nure.julia;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@PropertySource("classpath:application-hystrix.properties")
@EnableHystrix
@EnableCircuitBreaker
public @interface ApplyHystrixConfiguration {
}

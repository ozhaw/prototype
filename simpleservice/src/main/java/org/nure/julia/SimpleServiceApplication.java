package org.nure.julia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;

@SpringBootApplication
@EnableDiscoveryClient
@EnablePrometheusMetrics
public class SimpleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleServiceApplication.class, args);
    }

}


package org.nure.julia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eureka")
public class EurekaDiscoveryController {
    private final DiscoveryClient discoveryClient;

    @Autowired
    public EurekaDiscoveryController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/applications")
    public ResponseEntity getEurekaApplications() {
        return ResponseEntity.ok(discoveryClient.getServices());
    }
}

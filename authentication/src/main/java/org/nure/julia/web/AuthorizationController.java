package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${spring.application.name}.AuthorizationController")
public interface AuthorizationController extends HystrixFallbackController {

    @PostMapping("/claim")
    ResponseEntity createSession();

    @GetMapping
    ResponseEntity getClaim(@RequestHeader("Authorization") String token);

    @GetMapping("/verify")
    ResponseEntity verify(@RequestHeader("Authorization") String token);

    @PatchMapping
    ResponseEntity revokeSession(@RequestHeader("Authorization") String token);

    @DeleteMapping
    ResponseEntity dismissSession(@RequestHeader("Authorization") String token);

}

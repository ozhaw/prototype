package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.nure.julia.model.Claim;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authentication-service")
public interface AuthorizationController extends HystrixFallbackController {

    @PostMapping("/claim")
    ResponseEntity createSession(@RequestBody Claim claim);

    @GetMapping
    ResponseEntity getClaim(@RequestHeader("Authorization") String token);

    @PatchMapping
    ResponseEntity revokeSession(@RequestHeader("Authorization") String token);

    @DeleteMapping
    ResponseEntity dismissSession(@RequestHeader("Authorization") String token);

}

package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.nure.julia.web.WebControllerDefinitions.CLAIM_URL;
import static org.nure.julia.web.WebControllerDefinitions.VERIFY_URL;

@FeignClient(name = "${spring.application.name}.AuthorizationController")
public interface AuthorizationController extends HystrixFallbackController {

    @PostMapping(CLAIM_URL)
    ResponseEntity createSession();

    @GetMapping
    ResponseEntity getClaim(@RequestHeader("Authorization") String token);

    @GetMapping(VERIFY_URL)
    ResponseEntity verify(@RequestHeader("Authorization") String token);

    @PatchMapping
    ResponseEntity revokeSession(@RequestHeader("Authorization") String token);

    @DeleteMapping
    ResponseEntity dismissSession(@RequestHeader("Authorization") String token);

}

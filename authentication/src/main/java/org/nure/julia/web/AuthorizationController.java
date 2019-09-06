package org.nure.julia.web;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.nure.julia.model.Claim;
import org.nure.julia.model.Session;
import org.nure.julia.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(value = "hello", description = "Sample hello world application")
public class AuthorizationController {

    private final SessionService sessionService;

    @Autowired
    public AuthorizationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/claim")
    public ResponseEntity<Session> createSession(@RequestBody Claim claim) {
        return ResponseEntity.ok(sessionService.addSession(claim));
    }

    @GetMapping
    public ResponseEntity getClaim(@RequestHeader("Authorization") String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok(sessionService.getClaim(token.split(StringUtils.SPACE)[1]))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping
    public ResponseEntity revokeSession(@RequestHeader("Authorization") String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok(sessionService.revokeSession(token.split(StringUtils.SPACE)[1]))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping
    public ResponseEntity dismissSession(@RequestHeader("Authorization") String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                && sessionService.dismissSession(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}

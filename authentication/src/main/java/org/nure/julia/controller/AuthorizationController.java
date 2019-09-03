package org.nure.julia.controller;

import org.apache.commons.lang3.StringUtils;
import org.nure.julia.model.Session;
import org.nure.julia.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/auth")
public class AuthorizationController {

    private final SessionService sessionService;

    @Autowired
    public AuthorizationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Session> createSession() {
        return ResponseEntity.ok(sessionService.addSession());
    }

    @PostMapping("/verify")
    public ResponseEntity verifySession(@RequestBody String token) {
        return sessionService.isSessionActive(token)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping
    public ResponseEntity revokeSession(@RequestBody String token) {
        return ResponseEntity.ok(sessionService.revokeSession(token));
    }

    @DeleteMapping
    public ResponseEntity dismissSession(@RequestBody String token) {
        return sessionService.dismissSession(token)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}

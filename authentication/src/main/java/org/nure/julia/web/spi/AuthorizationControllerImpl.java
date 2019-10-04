package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.nure.julia.model.Claim;
import org.nure.julia.model.Session;
import org.nure.julia.service.spi.SessionManagementService;
import org.nure.julia.web.AuthorizationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthorizationControllerImpl implements AuthorizationController {

    private final SessionManagementService sessionService;

    @Autowired
    public AuthorizationControllerImpl(SessionManagementService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity createSession(Claim claim) {
        return ResponseEntity.ok(sessionService.addSession(claim));
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity getClaim(@RequestHeader("Authorization") String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok(sessionService.getClaim(token.split(StringUtils.SPACE)[1]))
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity verify(String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity revokeSession(@RequestHeader("Authorization") String token) {
        Session session = sessionService.revokeSession(token.split(StringUtils.SPACE)[1]);
        return session != null
                ? ResponseEntity.ok(session)
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity dismissSession(@RequestHeader("Authorization") String token) {
        return sessionService.isSessionActive(token.split(StringUtils.SPACE)[1])
                && sessionService.dismissSession(token.split(StringUtils.SPACE)[1])
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @SuppressWarnings("unchecked")
    private ResponseEntity fallback(String f) {
        return this.defaultFallback();
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity fallback(Claim f) {
        return this.defaultFallback();
    }

}

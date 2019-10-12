package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.nure.julia.model.Session;
import org.nure.julia.model.SessionStatus;
import org.nure.julia.service.spi.SessionManagementService;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.AuthorizationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@ApplicationController
@RequestMapping("/authentication/api/authentication")
public class AuthorizationControllerImpl implements AuthorizationController {

    private final SessionManagementService sessionService;

    @Autowired
    public AuthorizationControllerImpl(SessionManagementService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity createSession() {
        return ResponseEntity.ok(sessionService.addSession());
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity getClaim(@RequestHeader("Authorization") String token) {
        SessionStatus sessionStatus = sessionService.getSessionStatus(token.split(StringUtils.SPACE)[1]);
        return sessionStatus == SessionStatus.ACTIVE
                ? ResponseEntity.ok(sessionService.getClaim(token.split(StringUtils.SPACE)[1]))
                : sessionStatus == SessionStatus.EXPIRED
                    ? ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).build()
                    : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback")
    public ResponseEntity verify(String token) {
        SessionStatus sessionStatus = sessionService.getSessionStatus(token.split(StringUtils.SPACE)[1]);
        return sessionStatus == SessionStatus.ACTIVE
                ? ResponseEntity.ok().build()
                : sessionStatus == SessionStatus.EXPIRED
                    ? ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).build()
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
        SessionStatus sessionStatus = sessionService.getSessionStatus(token.split(StringUtils.SPACE)[1]);
        return (sessionStatus == SessionStatus.ACTIVE || sessionStatus == SessionStatus.EXPIRED)
                && sessionService.dismissSession(token.split(StringUtils.SPACE)[1])
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity fallback(String f) {
        return this.defaultFallback();
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity fallback() {
        return this.defaultFallback();
    }

}

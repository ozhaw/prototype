package org.nure.julia.service.spi;

import org.nure.julia.model.Session;
import org.nure.julia.model.SessionStatus;
import org.nure.julia.repository.SessionRepository;
import org.nure.julia.service.SessionService;
import org.nure.julia.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SessionManagementService implements SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManagementService.class);
    private static final long LIFE_TIME = 60000;

    @Value("${sessions.security.tokenLifeTime}")
    private long minutes;

    private final SessionRepository sessionRepository;
    private final TokenService tokenService;

    @Autowired
    public SessionManagementService(SessionRepository sessionRepository, TokenService tokenService) {
        this.sessionRepository = sessionRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Session addSession() {
        LOGGER.info("Creating new Session");

        Session session = new Session(tokenService.requestToken(), LIFE_TIME * minutes);
        sessionRepository.save(session);

        return session;
    }

    @Override
    public SessionStatus getSessionStatus(String token) {
        LOGGER.info("Checking session activity");

        Optional<Session> session = getSession(token);

        return session.map(value -> isSessionExpired(value)
                ? SessionStatus.EXPIRED : SessionStatus.ACTIVE).orElse(SessionStatus.INVALID);
    }

    @Override
    public Session revokeSession(String token) {
        LOGGER.info("Updating session");

        Optional<Session> session = getSession(token);
        if (session.isPresent()) {
            dismissSession(token);

            Session newSession = new Session(session.get().getToken(), session.get().getValidationKey(), LIFE_TIME * minutes);
            sessionRepository.save(newSession);

            return newSession;
        }
        return null;
    }

    @Override
    public boolean dismissSession(String token) {
        Optional<Session> session = getSession(token);
        if (session.isPresent()) {
            sessionRepository.delete(session.get());
            return true;
        }
        return false;
    }

    @Override
    public Session getClaim(String token) {
        return getSession(token).orElse(null);
    }

    private boolean isSessionExpired(Session session) {
        return session.getExpirationDate().compareTo(new Date()) < 0;
    }

    private Optional<Session> getSession(String token) {
        return sessionRepository.findById(token);
    }

}

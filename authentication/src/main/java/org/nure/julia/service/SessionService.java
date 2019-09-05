package org.nure.julia.service;

import org.nure.julia.model.Claim;
import org.nure.julia.model.Session;

public interface SessionService {

    Session addSession(Claim claim);

    boolean isSessionActive(String token);

    Session revokeSession(String token);

    boolean dismissSession(String token);

    Claim getClaim(String token);

}

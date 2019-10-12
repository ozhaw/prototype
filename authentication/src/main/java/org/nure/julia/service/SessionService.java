package org.nure.julia.service;

import org.nure.julia.model.Session;
import org.nure.julia.model.SessionStatus;

public interface SessionService {

    Session addSession();

    SessionStatus getSessionStatus(String token);

    Session revokeSession(String token);

    boolean dismissSession(String token);

    Session getClaim(String token);

}

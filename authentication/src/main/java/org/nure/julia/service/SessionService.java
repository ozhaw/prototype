package org.nure.julia.service;

import org.nure.julia.model.Session;

public interface SessionService {

    Session addSession();

    boolean isSessionActive(String token);

    Session revokeSession(String token);

    boolean dismissSession(String token);

}

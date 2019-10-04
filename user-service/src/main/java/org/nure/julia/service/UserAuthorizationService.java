package org.nure.julia.service;

import org.nure.julia.dto.SessionDto;
import org.nure.julia.entity.user.WebUser;

import java.util.Optional;

public interface UserAuthorizationService {
    Optional<SessionDto> registerClaim(WebUser webUser);
}

package org.nure.julia.service;

import org.nure.julia.dto.UserSessionDto;

import java.util.Optional;

public interface UserAuthorizationService {
    Optional<UserSessionDto> registerClaim();
}

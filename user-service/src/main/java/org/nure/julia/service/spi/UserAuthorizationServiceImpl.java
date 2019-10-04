package org.nure.julia.service.spi;

import org.nure.julia.SecurityWebFilter;
import org.nure.julia.dto.ClaimIdentityDto;
import org.nure.julia.dto.SessionDto;
import org.nure.julia.entity.user.WebUser;
import org.nure.julia.service.UserAuthorizationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    private final RestTemplate restTemplate;
    private final SecurityWebFilter securityWebFilter;

    public UserAuthorizationServiceImpl(RestTemplate restTemplate, SecurityWebFilter securityWebFilter) {
        this.restTemplate = restTemplate;
        this.securityWebFilter = securityWebFilter;
    }

    @Override
    public Optional<SessionDto> registerClaim(WebUser webUser) {
        ClaimIdentityDto claimIdentityDto = new ClaimIdentityDto();
        claimIdentityDto.setClaimKey(webUser.getClaimKey());
        claimIdentityDto.setIdentifier(String.valueOf(webUser.getId()));

        ResponseEntity<SessionDto> response = restTemplate.exchange(
                securityWebFilter.getAuthenticationServiceURL(),
                HttpMethod.POST,
                new HttpEntity<>(claimIdentityDto),
                SessionDto.class
        );

        return response.getStatusCode() == HttpStatus.OK
                ? Optional.ofNullable(response.getBody())
                : Optional.empty();
    }
}

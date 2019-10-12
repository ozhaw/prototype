package org.nure.julia.service.spi;

import kong.unirest.HttpResponse;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import org.nure.julia.dto.UserSessionDto;
import org.nure.julia.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    private final ObjectMapper internalObjectMapper;
    private final String authenticationServiceURL;

    @Autowired
    public UserAuthorizationServiceImpl(ObjectMapper internalObjectMapper, String authenticationServiceURL) {
        this.internalObjectMapper = internalObjectMapper;
        this.authenticationServiceURL = authenticationServiceURL;
    }

    @Override
    public Optional<UserSessionDto> registerClaim() {
        HttpResponse<UserSessionDto> response = Unirest.post(authenticationServiceURL + "/claim")
                .header("Content-Type", "application/json")
                .charset(StandardCharsets.UTF_8)
                .withObjectMapper(internalObjectMapper)
                .asObject(UserSessionDto.class);

        return response.getStatus() == HttpStatus.OK.value()
                ? Optional.ofNullable(response.getBody())
                : Optional.empty();
    }
}

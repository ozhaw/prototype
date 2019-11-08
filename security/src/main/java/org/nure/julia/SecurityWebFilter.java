package org.nure.julia;

import org.nure.julia.dto.UserSessionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SecurityWebFilter extends OncePerRequestFilter {
    private String authenticationUrl;

    public SecurityWebFilter(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    @SuppressWarnings({"NullableProblems"})
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Optional<UserSessionDto> claimIdentity = getSession(token);
            if (claimIdentity.isPresent()) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private Optional<UserSessionDto> getSession(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);

        try {
            ResponseEntity<UserSessionDto> response = restTemplate()
                    .exchange(authenticationUrl, HttpMethod.GET, new HttpEntity(headers), UserSessionDto.class);

            return response.getStatusCode() == HttpStatus.OK
                    ? Optional.ofNullable(response.getBody())
                    : response.getStatusCode() == HttpStatus.UPGRADE_REQUIRED
                        ? revokeSession(token)
                        : Optional.empty();
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    private Optional<UserSessionDto> revokeSession(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);

        try {
            ResponseEntity<UserSessionDto> response = restTemplate()
                    .exchange(authenticationUrl, HttpMethod.PATCH, new HttpEntity(headers), UserSessionDto.class);

            return response.getStatusCode() == HttpStatus.OK
                    ? Optional.ofNullable(response.getBody())
                    : Optional.empty();
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

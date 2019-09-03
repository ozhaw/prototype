package org.nure.julia.config;

import org.apache.commons.lang3.StringUtils;
import org.nure.julia.repository.SessionRepository;
import org.springframework.lang.NonNull;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

public class SecurityFilter extends OncePerRequestFilter {

    private SessionRepository sessionRepository;
    private String tokenKey;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        initContextData(httpServletRequest);

        String token = httpServletRequest.getHeader("Authorization");
        if (token == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            if (token.startsWith(tokenKey + StringUtils.SPACE)) {
                String tokenValue = token.split(StringUtils.SPACE)[1];
                if (!isSessionExists(tokenValue)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                }
            }
        }
    }

    private void initContextData(HttpServletRequest httpServletRequest) {
        final WebApplicationContext applicationContext =
                getWebApplicationContext(httpServletRequest.getServletContext());
        sessionRepository = applicationContext.getBean(SessionRepository.class);
        tokenKey = defaultIfEmpty(applicationContext.getEnvironment().getProperty("sessions.security.tokenKey"), "Bearer");
    }

    private boolean isSessionExists(String token) {
        return sessionRepository.findById(token).isPresent();
    }

}

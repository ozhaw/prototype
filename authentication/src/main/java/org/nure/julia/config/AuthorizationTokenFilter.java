package org.nure.julia.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {

        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && !token.startsWith("Bearer" + StringUtils.SPACE)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

}

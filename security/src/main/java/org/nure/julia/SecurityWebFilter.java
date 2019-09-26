package org.nure.julia;

import org.apache.commons.lang.StringUtils;
import org.nure.julia.dto.ClaimIdentityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class SecurityWebFilter extends OncePerRequestFilter {

    @Value("${default.security.context}")
    private String securityContextPath;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public SecurityWebFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @SuppressWarnings({"NullableProblems"})
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Optional<ClaimIdentityDto> claimIdentity = getClaimIdentity(token);
            if (claimIdentity.isPresent()) {
                ClaimIdentityDto claim = claimIdentity.get();
                MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(request);
                mutableHttpServletRequest.putHeader("ClaimIdentity", claim.getIdentifier());
                mutableHttpServletRequest.putHeader("ClaimKey", claim.getClaimKey());

                filterChain.doFilter(mutableHttpServletRequest, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private Optional<ClaimIdentityDto> getClaimIdentity(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);

        try {
            ResponseEntity<ClaimIdentityDto> response = restTemplate()
                    .exchange(getAuthenticationServiceURL(), HttpMethod.GET, new HttpEntity(headers), ClaimIdentityDto.class);

            return response.getStatusCode() == HttpStatus.OK
                    ? Optional.ofNullable(response.getBody())
                    : Optional.empty();
        } catch (RestClientException e) {
            return Optional.empty();
        }

    }

    private String getAuthenticationServiceURL() {
        List<ServiceInstance> gatewayInstances = discoveryClient.getInstances("gateway-service");
        if (gatewayInstances == null || gatewayInstances.isEmpty()) {
            List<ServiceInstance> authInstances = discoveryClient.getInstances("authentication-service");
            return gatewayInstances == null || gatewayInstances.isEmpty()
                    ? StringUtils.EMPTY
                    : authInstances.get(0).getUri() + securityContextPath;
        } else {
            return gatewayInstances.get(0).getUri() + securityContextPath;
        }
    }

    private final class MutableHttpServletRequest extends HttpServletRequestWrapper {

        private final Map<String, String> customHeaders;

        MutableHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.customHeaders = new HashMap<>();
        }

        void putHeader(String name, String value) {
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name) {
            String headerValue = customHeaders.get(name);

            if (headerValue != null) {
                return headerValue;
            }

            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        public Enumeration<String> getHeaderNames() {
            Set<String> set = new HashSet<>(customHeaders.keySet());

            @SuppressWarnings("unchecked")
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                String n = e.nextElement();
                set.add(n);
            }

            return Collections.enumeration(set);
        }
    }

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

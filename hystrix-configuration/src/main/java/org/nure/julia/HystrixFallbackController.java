package org.nure.julia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.requireNonNull;

public interface HystrixFallbackController {

    default ResponseEntity defaultFallback() {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Request time exceed");
    }

    default String getHeader(HttpServletRequest httpServletRequest, String header) {
        return requireNonNull(httpServletRequest.getHeader(header));
    }

}

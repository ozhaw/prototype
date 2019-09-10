package org.nure.julia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface HystrixFallbackController {

    default ResponseEntity defaultFallback() {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Request time exceed");
    }

}

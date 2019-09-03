package org.nure.julia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/error")
    public ResponseEntity<List> fallback(){
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body(Collections.singletonList("Request was timed out"));
    }

}

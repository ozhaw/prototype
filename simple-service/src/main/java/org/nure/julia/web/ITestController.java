package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "simple-service")
public interface ITestController extends HystrixFallbackController {

    @GetMapping("/status/check")
    ResponseEntity status();

}

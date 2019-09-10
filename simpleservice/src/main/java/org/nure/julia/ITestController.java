package org.nure.julia;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "simple-service")
public interface ITestController extends HystrixFallbackController {

    @GetMapping("/status/check")
    ResponseEntity status();

}

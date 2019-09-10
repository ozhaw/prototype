package org.nure.julia;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simple")
public class TestController implements ITestController {

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity status() {
        return ResponseEntity.ok("Working");
    }


    private ResponseEntity fallback() {
        return this.defaultFallback();
    }

}

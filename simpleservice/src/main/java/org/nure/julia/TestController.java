package org.nure.julia;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Timed
@RestController
@RequestMapping("/api/simple")
public class TestController {

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }

}

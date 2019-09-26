package org.nure.julia.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/simple")
@ApplicationController
public class TestController implements ITestController {

    @Value("${spring.application.name}.outbound")
    private String topic;

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity status() {
        return ResponseEntity.ok("Working");
    }

    /*@JmsListener(destination = "${spring.application.name}.inbound")
    public void abc(final Message message) throws JMSException {
        Optional<String> m = ListenerTemplate.receiveText(message);
        m.isPresent();
    }*/

    @SuppressWarnings("unchecked")
    private ResponseEntity fallback() {
        return this.defaultFallback();
    }

}

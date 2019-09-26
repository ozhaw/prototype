package org.nure.julia.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orn.nure.julia.ListenerTemplate;

import javax.jms.JMSException;
import java.util.Optional;

@RestController
@RequestMapping("/api/simple")
@CrossOrigin
@ApiOperation(value = "", authorizations = @Authorization(value = "Bearer"))
public class TestController implements ITestController {

    @Value("${spring.application.name}.outbound")
    private String topic;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public TestController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity status() {
        jmsTemplate.convertAndSend(topic, "W");
        return ResponseEntity.ok("Working");
    }

    @JmsListener(destination = "${spring.application.name}.inbound")
    public void abc(final Message message) throws JMSException {
        Optional<String> m = ListenerTemplate.receiveText(message);
        m.isPresent();
    }


    private ResponseEntity fallback() {
        return this.defaultFallback();
    }

}

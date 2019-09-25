package org.nure.julia;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orn.nure.julia.ListenerTemplate;

import javax.jms.JMSException;
import java.util.Optional;

@RestController
@RequestMapping("/simple")
public class TestController implements ITestController {

    @Value("${spring.application.name}")
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

    @JmsListener(destination = "${spring.application.name}")
    public void abc(final Message message) throws JMSException {
        Optional<String> m = ListenerTemplate.receiveText(message);
        m.isPresent();
    }


    private ResponseEntity fallback() {
        return this.defaultFallback();
    }

}

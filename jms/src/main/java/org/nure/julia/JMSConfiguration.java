package org.nure.julia;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JMSConfiguration {

    @Bean
    public BrokerService broker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61698");
        broker.setPersistent(false);
        broker.setUseJmx(true);
        return broker;
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}

package orn.nure.julia.notifications;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JMSReceiver {

    @JmsListener(destination = "app.device")
    public void receiveDeviceData(String deviceData) {
        System.out.println("Received <" + deviceData + ">");
    }

    @JmsListener(destination = "app.health")
    public void receiveHealthData(String healthData) {
        System.out.println("Received <" + healthData + ">");
    }

}

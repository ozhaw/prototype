package orn.nure.julia.listeners;

import javax.jms.Message;
import javax.jms.MessageListener;

public class DeviceDataListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(message.toString());
    }

}

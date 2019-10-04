package orn.nure.julia.notifications;

import com.google.common.collect.ImmutableMap;
import com.pusher.pushnotifications.PushNotifications;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import orn.nure.julia.notifications.dto.spi.FCMDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationsService {

    @Value("${pusher.instanceId}")
    private String instanceId;

    @Value("${pusher.secret.key}")
    private String secretKey;

    private final PushNotifications pushClient;

    public NotificationsService() {
        this.pushClient = new PushNotifications(instanceId, secretKey);
    }

    public void notify(final FCMDto fcmDto, String... clients) {
        this.notify(fcmDto, Arrays.asList(clients));
    }

    public boolean notify(final FCMDto fcmDto, final List<String> clients) {
        try {
            pushClient.publishToInterests(clients, ImmutableMap.of("fcm", fcmDto.toMap()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

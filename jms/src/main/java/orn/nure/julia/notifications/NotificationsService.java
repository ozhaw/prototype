package orn.nure.julia.notifications;

import com.google.common.collect.ImmutableMap;
import com.pusher.pushnotifications.PushNotifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import orn.nure.julia.notifications.dto.spi.FCMDto;

import java.util.Arrays;
import java.util.List;

@Service
public class NotificationsService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationsService.class);

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
            LOG.error("Unable to push message", e);
            return false;
        }
        return true;
    }

}

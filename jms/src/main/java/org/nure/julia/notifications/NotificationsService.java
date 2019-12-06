package org.nure.julia.notifications;

import com.google.common.collect.ImmutableMap;
import com.pusher.pushnotifications.PushNotifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.nure.julia.notifications.dto.spi.FCMDto;

import java.util.Arrays;
import java.util.List;

@Service
public class NotificationsService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationsService.class);

    private final String instanceId = "9f9f0337-33b7-4ce6-afb3-0585c253506d";
    private final String secretKey = "C0D22731B2BF77682E2ADB4BD3BE2620D2D30AD0FCE5A71DD0DAA709E6CE825F";

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

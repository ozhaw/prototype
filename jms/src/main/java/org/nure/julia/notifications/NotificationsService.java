package org.nure.julia.notifications;

import com.google.common.collect.ImmutableMap;
import com.pusher.pushnotifications.PushNotifications;
import org.nure.julia.entity.WebUser;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.notifications.dto.spi.FCMDto;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.web.dto.NotificationRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class NotificationsService {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationsService.class);

    private final String INSTANCE_ID = "9f9f0337-33b7-4ce6-afb3-0585c253506d";
    private final String SECRET = "C0D22731B2BF77682E2ADB4BD3BE2620D2D30AD0FCE5A71DD0DAA709E6CE825F";

    private final PushNotifications pushClient;
    private final UserRepository userRepository;

    @Autowired
    public NotificationsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.pushClient = new PushNotifications(INSTANCE_ID, SECRET);
    }

    public void notify(final FCMDto fcmDto, String... clients) {
        this.notify(fcmDto, Arrays.asList(clients));
    }

    public void notify(final FCMDto fcmDto, final List<String> clients) {
        try {
            pushClient.publishToInterests(clients, ImmutableMap.of("fcm", fcmDto.toMap()));
        } catch (Exception e) {
            LOG.error("Unable to push message", e);
        }
    }

    public void test(final Long userId, final NotificationRequestDto notificationRequestDto) {
        WebUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User was not found"));

        final FCMDto fcmDto = FCMDto.builder()
                .setAdvice(notificationRequestDto.getAdvice())
                .setDate(new Date())
                .setSeverity(notificationRequestDto.getSeverity())
                .setBody(notificationRequestDto.getBody())
                .setTitle(notificationRequestDto.getTitle())
                .setType(notificationRequestDto.getType())
                .build();

        notify(fcmDto, user.getEmail());
    }

}

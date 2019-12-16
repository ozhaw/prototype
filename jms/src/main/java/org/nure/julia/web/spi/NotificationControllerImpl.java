package org.nure.julia.web.spi;

import org.nure.julia.notifications.NotificationsService;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.NotificationController;
import org.nure.julia.web.dto.NotificationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.nure.julia.web.WebControllerDefinitions.BASE_URL;

@ApplicationController
@RequestMapping(BASE_URL)
public class NotificationControllerImpl implements NotificationController {

    private final NotificationsService notificationsService;

    @Autowired
    public NotificationControllerImpl(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @Override
    public ResponseEntity testNotification(Long userId, NotificationRequestDto notificationRequestDto) {
        this.notificationsService.test(userId, notificationRequestDto);
        return ResponseEntity.ok().build();
    }
}

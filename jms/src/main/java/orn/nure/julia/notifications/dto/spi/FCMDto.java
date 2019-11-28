package orn.nure.julia.notifications.dto.spi;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FCMDto {
    private NotifDataDto data = new NotifDataDto();
    private NotificationDto notification = new NotificationDto();

    public NotifDataDto getData() {
        return data;
    }

    public void setData(NotifDataDto data) {
        this.data = data;
    }

    public NotificationDto getNotification() {
        return notification;
    }

    public void setNotification(NotificationDto notification) {
        this.notification = notification;
    }

    public Map<String, Map<String, String>> toMap() {
        return ImmutableMap.of("notification", notification.toMap(), "data", data.toMap());
    }
}

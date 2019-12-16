package org.nure.julia.notifications.dto.spi;

import com.google.common.collect.ImmutableMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FCMDto {
    private NotifDataDto data;
    private NotificationDto notification;

    public FCMDto() {
        this.data = new NotifDataDto();
        this.notification = new NotificationDto();
    }

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

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private FCMDto fcmDto;

        private NotificationBuilder() {
            fcmDto = new FCMDto();
        }

        public NotificationBuilder setDate(Date date) {
            this.fcmDto.getData().setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            return this;
        }

        public NotificationBuilder setDate(String date) {
            this.fcmDto.getData().setTime(date);
            return this;
        }

        public NotificationBuilder setSeverity(String severity) {
            this.fcmDto.getData().setSeverity(severity);
            return this;
        }

        public NotificationBuilder setAdvice(String advice) {
            this.fcmDto.getData().setAdvice(advice);
            return this;
        }

        public NotificationBuilder setTitle(String title) {
            this.fcmDto.getNotification().setTitle(title);
            return this;
        }

        public NotificationBuilder setBody(String body) {
            this.fcmDto.getNotification().setBody(body);
            return this;
        }

        public FCMDto build() {
            return this.fcmDto;
        }
    }
}

package org.nure.julia.notifications.dto.spi;

import org.nure.julia.notifications.dto.Mappable;

public class NotificationDto implements Mappable {
    private String title;
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

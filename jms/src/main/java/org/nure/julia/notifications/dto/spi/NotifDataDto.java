package org.nure.julia.notifications.dto.spi;

import org.nure.julia.notifications.dto.Mappable;

public class NotifDataDto implements Mappable {
    private String time;
    private String severity;
    private String advice;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}

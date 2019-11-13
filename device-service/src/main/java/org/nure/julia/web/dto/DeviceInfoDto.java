package org.nure.julia.web.dto;

import org.nure.julia.misc.DeviceStatus;

import java.util.Date;

public class DeviceInfoDto {
    private Date auditDate;
    private DeviceStatus deviceStatus;

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}

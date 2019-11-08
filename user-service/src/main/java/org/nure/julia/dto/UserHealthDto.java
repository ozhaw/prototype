package org.nure.julia.dto;

import org.nure.julia.misc.HealthStatus;

import java.util.Date;

public class UserHealthDto {
    private Date auditDate;
    private HealthStatus healthStatus;

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }
}

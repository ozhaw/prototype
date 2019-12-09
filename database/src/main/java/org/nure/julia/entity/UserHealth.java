package org.nure.julia.entity;

import org.nure.julia.misc.HealthStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "webuser_health")
public class UserHealth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_date", nullable = false, updatable = false)
    private Date auditDate;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public UserHealth() {
    }

    public UserHealth(HealthStatus healthStatus, Date auditDate, Device device) {
        this.healthStatus = healthStatus;
        this.auditDate = auditDate;
        this.device = device;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return this.device;
    }
}

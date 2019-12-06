package org.nure.julia.entity;

import org.nure.julia.misc.DeviceStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device_info")
public class DeviceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_date", nullable = false, updatable = false)
    private Date auditDate;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false, updatable = false)
    private Device device;

    public DeviceInfo() { }

    public DeviceInfo(DeviceStatus deviceStatus, Date auditDate) {
        this.deviceStatus = deviceStatus;
        this.auditDate = auditDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
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

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}

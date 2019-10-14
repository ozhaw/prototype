package org.nure.julia.entity;

import org.nure.julia.misc.HealthStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "device")
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deviceId", nullable = false, unique = true, updatable = false)
    private String deviceId;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "webuser_id", nullable = false)
    private WebUser webUser;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<HealthStatus> healthStatuses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public Set<HealthStatus> getHealthStatuses() {
        return healthStatuses;
    }

    public void setHealthStatuses(Set<HealthStatus> healthStatuses) {
        this.healthStatuses = healthStatuses;
    }
}

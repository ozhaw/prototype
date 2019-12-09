package org.nure.julia.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nure.julia.entity.DeviceInfo;
import org.nure.julia.entity.UserHealth;
import org.nure.julia.listeners.dto.DeviceInfoDto;
import org.nure.julia.listeners.dto.HealthInfoDto;
import org.nure.julia.misc.DeviceStatus;
import org.nure.julia.misc.HealthStatus;
import org.nure.julia.repository.DeviceInfoRepository;
import org.nure.julia.repository.DeviceRepository;
import org.nure.julia.repository.UserHealthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
public class JMSReceiver {
    private final static Logger LOG = LoggerFactory.getLogger(JMSReceiver.class);
    private final ObjectMapper mapper;
    private final DeviceRepository deviceRepository;
    private final UserHealthRepository userHealthRepository;
    private final DeviceInfoRepository deviceInfoRepository;

    public JMSReceiver(ObjectMapper mapper, DeviceRepository deviceRepository,
                       UserHealthRepository userHealthRepository, DeviceInfoRepository deviceInfoRepository) {
        this.mapper = mapper;
        this.deviceRepository = deviceRepository;
        this.userHealthRepository = userHealthRepository;
        this.deviceInfoRepository = deviceInfoRepository;
    }

    @JmsListener(destination = "app.device")
    @Transactional
    public void receiveDeviceData(String deviceData) {
        try {
            final DeviceInfoDto deviceInfoDto = mapper.readValue(deviceData, DeviceInfoDto.class);

            deviceRepository.findByDeviceId(deviceInfoDto.getDeviceId())
                    .ifPresent(device -> {
                       deviceInfoRepository.save(new DeviceInfo(DeviceStatus.valueOf(deviceInfoDto.getDeviceStatus()),
                                deviceInfoDto.getAuditDate(), device));
                    });
        } catch (IOException e) {
            LOG.error("Unable to read data from Queue", e);
        }
    }

    @JmsListener(destination = "app.health")
    @Transactional
    public void receiveHealthData(String healthData) {
        try {
            final HealthInfoDto healthInfoDto = mapper.readValue(healthData, HealthInfoDto.class);

            deviceRepository.findByDeviceId(healthInfoDto.getDeviceId())
                    .ifPresent(device -> {
                        userHealthRepository.save(new UserHealth(HealthStatus.valueOf(healthInfoDto.getHealthStatus()),
                                healthInfoDto.getAuditDate(), device));
                    });
        } catch (IOException e) {
            LOG.error("Unable to read data from Queue", e);
        }
    }

}

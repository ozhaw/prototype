package org.nure.julia.web.service;

import org.nure.julia.web.dto.DeviceDto;

public interface DeviceService {
    boolean addDevice(Long userId, final DeviceDto deviceDto);

    DeviceDto getDeviceByDeviceId(String deviceId);

    DeviceDto getDeviceById(Long id);
}

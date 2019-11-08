package org.nure.julia.web.service;

import org.nure.julia.web.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    boolean addDevice(Long userId, final DeviceDto deviceDto);

    DeviceDto getDeviceByDeviceId(String deviceId);

    DeviceDto getDeviceById(Long id);

    List<DeviceDto> getDevicesForUser(Long userId);
}

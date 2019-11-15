package org.nure.julia.service;

import org.nure.julia.dto.DeviceDto;

import java.util.List;

public interface DeviceService {
    boolean addDevice(Long userId, final DeviceDto deviceDto);

    DeviceDto getDeviceByDeviceId(String deviceId);

    DeviceDto getDeviceById(Long id);

    List<DeviceDto> getDevicesForUser(Long userId);
}

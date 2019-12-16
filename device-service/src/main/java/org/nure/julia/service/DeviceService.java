package org.nure.julia.service;

import org.nure.julia.dto.DeviceDto;
import org.nure.julia.entity.Device;

import java.util.List;

public interface DeviceService {
    DeviceDto addDevice(Long userId, final DeviceDto deviceDto);

    DeviceDto getDeviceByDeviceId(String deviceId);

    DeviceDto getDeviceById(Long id);

    List<DeviceDto> getDevicesForUser(Long userId);
}

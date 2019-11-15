package org.nure.julia.service;

import org.nure.julia.dto.DeviceInfoDto;

import java.util.List;

public interface DeviceInfoService {
    List<DeviceInfoDto> getDeviceInfoForDevice(Long deviceId);
}

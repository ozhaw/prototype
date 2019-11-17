package org.nure.julia.service;

import org.nure.julia.dto.DeviceInfoDto;

import java.util.List;
import java.util.Map;

public interface DeviceInfoService {
    List<DeviceInfoDto> getDeviceInfoForDevice(Long deviceId);

    Map<String, List<DeviceInfoDto>> getDeviceInfo(Long userId);
}

package org.nure.julia.web.service;

import org.nure.julia.web.dto.DeviceInfoDto;

import java.util.List;

public interface DeviceInfoService {
    List<DeviceInfoDto> getDeviceInfoForDevice(Long deviceId);
}

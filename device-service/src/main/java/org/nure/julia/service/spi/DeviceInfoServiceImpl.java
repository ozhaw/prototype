package org.nure.julia.service.spi;

import org.nure.julia.entity.DeviceInfo;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.repository.DeviceRepository;
import org.nure.julia.dto.DeviceInfoDto;
import org.nure.julia.exceptions.DeviceNotFoundException;
import org.nure.julia.service.DeviceInfoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

    private final DeviceRepository deviceRepository;
    private final BasicMapper<DeviceInfoDto, DeviceInfo> deviceInfoDtoDeviceInfoBasicMapper;

    public DeviceInfoServiceImpl(DeviceRepository deviceRepository,
                                 BasicMapper<DeviceInfoDto, DeviceInfo> deviceInfoDtoDeviceInfoBasicMapper) {

        this.deviceRepository = deviceRepository;
        this.deviceInfoDtoDeviceInfoBasicMapper = deviceInfoDtoDeviceInfoBasicMapper;
    }

    @Override
    @Transactional
    public List<DeviceInfoDto> getDeviceInfoForDevice(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device was not found"))
                .getDeviceInfoList()
                .stream()
                    .map(deviceInfoDtoDeviceInfoBasicMapper::reversalMap)
                    .collect(Collectors.toList());
    }

}

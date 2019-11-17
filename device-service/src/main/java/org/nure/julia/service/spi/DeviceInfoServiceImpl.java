package org.nure.julia.service.spi;

import org.nure.julia.entity.Device;
import org.nure.julia.entity.DeviceInfo;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.repository.DeviceRepository;
import org.nure.julia.dto.DeviceInfoDto;
import org.nure.julia.exceptions.DeviceNotFoundException;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.service.DeviceInfoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final BasicMapper<DeviceInfoDto, DeviceInfo> deviceInfoDtoDeviceInfoBasicMapper;

    public DeviceInfoServiceImpl(DeviceRepository deviceRepository,
                                 UserRepository userRepository,
                                 BasicMapper<DeviceInfoDto, DeviceInfo> deviceInfoDtoDeviceInfoBasicMapper) {

        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.deviceInfoDtoDeviceInfoBasicMapper = deviceInfoDtoDeviceInfoBasicMapper;
    }

    @Override
    @Transactional
    public List<DeviceInfoDto> getDeviceInfoForDevice(Long deviceId) {
        return retrieveAndMapDeviceInfo(deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device was not found")));
    }

    @Override
    @Transactional
    public Map<String, List<DeviceInfoDto>> getDeviceInfo(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User was not found"))
                .getDevices()
                .stream()
                .collect(Collectors.toMap(Device::getDeviceId, this::retrieveAndMapDeviceInfo));
    }

    private List<DeviceInfoDto> retrieveAndMapDeviceInfo(Device device) {
        return device.getDeviceInfoList().stream()
                .map(deviceInfoDtoDeviceInfoBasicMapper::reversalMap)
                .collect(Collectors.toList());
    }

}

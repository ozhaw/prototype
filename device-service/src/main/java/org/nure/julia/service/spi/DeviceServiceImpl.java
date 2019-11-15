package org.nure.julia.service.spi;

import org.nure.julia.entity.Device;
import org.nure.julia.entity.WebUser;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.repository.DeviceRepository;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.dto.DeviceDto;
import org.nure.julia.exceptions.DeviceNotFoundException;
import org.nure.julia.exceptions.UniqueDeviceAlreadyExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final BasicMapper<DeviceDto, Device> deviceDtoDeviceBasicMapper;

    public DeviceServiceImpl(UserRepository userRepository, DeviceRepository deviceRepository,
                             BasicMapper<DeviceDto, Device> deviceDtoDeviceBasicMapper) {

        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.deviceDtoDeviceBasicMapper = deviceDtoDeviceBasicMapper;
    }

    @Override
    public boolean addDevice(Long userId, final DeviceDto deviceDto) {
        if (deviceRepository.findByDeviceId(deviceDto.getDeviceId()).isPresent()) {
            throw new UniqueDeviceAlreadyExistsException("Device was already registered");
        }

        Optional<WebUser> webUser = userRepository.findById(userId);
        if (webUser.isPresent()) {
            Device device = deviceDtoDeviceBasicMapper.map(deviceDto);
            device.setWebUser(webUser.get());

            return !isNull(deviceRepository.save(device).getId());
        }

        return false;
    }

    @Override
    public DeviceDto getDeviceByDeviceId(String deviceId) {
        return deviceDtoDeviceBasicMapper.reversalMap(deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device doesn`t exist")));
    }

    @Override
    public DeviceDto getDeviceById(Long id) {
        return deviceDtoDeviceBasicMapper.reversalMap(deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device doesn`t exist")));
    }

    @Override
    public List<DeviceDto> getDevicesForUser(Long userId) {
        WebUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

        return user.getDevices().stream().map(deviceDtoDeviceBasicMapper::reversalMap).collect(Collectors.toList());
    }
}

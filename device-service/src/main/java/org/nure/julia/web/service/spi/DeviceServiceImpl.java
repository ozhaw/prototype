package org.nure.julia.web.service.spi;

import org.nure.julia.entity.Device;
import org.nure.julia.entity.WebUser;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.repository.DeviceRepository;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.web.dto.DeviceDto;
import org.nure.julia.web.exceptions.UniqueDeviceAlreadyExistsException;
import org.nure.julia.web.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
        return null;
    }

    @Override
    public DeviceDto getDeviceById(Long id) {
        return null;
    }
}

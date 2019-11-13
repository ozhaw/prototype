package org.nure.julia.web.mappings;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.nure.julia.entity.Device;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.web.dto.DeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeviceMapper implements BasicMapper<DeviceDto, Device> {

    private final ModelMapper modelMapper;

    private TypeMap<DeviceDto, Device> deviceDtoDeviceTypeMap;
    private TypeMap<Device, DeviceDto> deviceDeviceDtoTypeMap;

    @Autowired
    public DeviceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void register() {
        deviceDtoDeviceTypeMap = modelMapper.addMappings(new PropertyMap<DeviceDto, Device>() {
            @Override
            protected void configure() {
                map().setId(null);
                map().setDeviceId(source.getDeviceId());
                map().setType(source.getType());
            }
        });

        deviceDeviceDtoTypeMap = modelMapper.addMappings(new PropertyMap<Device, DeviceDto>() {
            @Override
            protected void configure() {
                map().setDeviceId(source.getDeviceId());
                map().setType(source.getType());
                map().setId(source.getId());
            }
        });
    }

    @Override
    public Device map(DeviceDto deviceDto) {
        return deviceDtoDeviceTypeMap.map(deviceDto);
    }

    @Override
    public DeviceDto reversalMap(Device device) {
        return deviceDeviceDtoTypeMap.map(device);
    }
}

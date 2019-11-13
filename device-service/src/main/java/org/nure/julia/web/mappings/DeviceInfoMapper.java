package org.nure.julia.web.mappings;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.nure.julia.entity.DeviceInfo;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.misc.DeviceStatus;
import org.nure.julia.web.dto.DeviceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Component
public class DeviceInfoMapper implements BasicMapper<DeviceInfoDto, DeviceInfo> {

    private final ModelMapper modelMapper;

    private TypeMap<DeviceInfoDto, DeviceInfo> deviceInfoDtoDeviceInfoTypeMap;
    private TypeMap<DeviceInfo, DeviceInfoDto> deviceInfoDeviceInfoDtoTypeMap;

    @Autowired
    public DeviceInfoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void register() {
        deviceInfoDtoDeviceInfoTypeMap = modelMapper.addMappings(new PropertyMap<DeviceInfoDto, DeviceInfo>() {
            @Override
            protected void configure() {
                map().setAuditDate(source.getAuditDate());
                map().setDeviceStatus(source.getDeviceStatus());
            }
        });

        deviceInfoDeviceInfoDtoTypeMap = modelMapper.addMappings(new PropertyMap<DeviceInfo, DeviceInfoDto>() {
            @Override
            protected void configure() {
                map().setAuditDate(source.getAuditDate());
                map().setDeviceStatus(source.getDeviceStatus());
            }
        });
    }

    @Override
    public DeviceInfo map(DeviceInfoDto deviceDto) {
        return deviceInfoDtoDeviceInfoTypeMap.map(deviceDto);
    }

    @Override
    public DeviceInfoDto reversalMap(DeviceInfo device) {
        return deviceInfoDeviceInfoDtoTypeMap.map(device);
    }
}

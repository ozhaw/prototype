package org.nure.julia.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.nure.julia.dto.UserHealthDto;
import org.nure.julia.entity.UserHealth;
import org.nure.julia.mappings.BasicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserHealthMapper implements BasicMapper<UserHealthDto, UserHealth> {

    private final ModelMapper modelMapper;

    private TypeMap<UserHealthDto, UserHealth> userHealthDtoToUserHealthTypeMap;
    private TypeMap<UserHealth, UserHealthDto> userHealthToUserHealthDtoTypeMap;

    @Autowired
    public UserHealthMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void register() {
        userHealthDtoToUserHealthTypeMap = modelMapper.addMappings(new PropertyMap<UserHealthDto, UserHealth>() {
            @Override
            protected void configure() {
                map().setAuditDate(source.getAuditDate());
                map().setHealthStatus(source.getHealthStatus());
            }
        });

        userHealthToUserHealthDtoTypeMap = modelMapper.addMappings(new PropertyMap<UserHealth, UserHealthDto>() {
            @Override
            protected void configure() {
                map().setAuditDate(source.getAuditDate());
                map().setHealthStatus(source.getHealthStatus());
            }
        });
    }

    @Override
    public UserHealth map(UserHealthDto userHealthDto) {
        return userHealthDtoToUserHealthTypeMap.map(userHealthDto);
    }

    @Override
    public UserHealthDto reversalMap(UserHealth userHealth) {
        return userHealthToUserHealthDtoTypeMap.map(userHealth);
    }
}

package org.nure.julia.service;

import org.nure.julia.dto.DeviceDto;
import org.nure.julia.dto.UserHealthDto;
import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean addUser(final WebUserDto webUserDto);

    WebUserDto updateUserInfo(final WebUserDto webUserDto, Long userId);

    WebUserDto getUserInfo(Long userId);

    WebUserDto authorizeUser(final WebUserCredentialsDto webUserCredentialsDto);

    Map<String, List<UserHealthDto>> getUserHealthInfo(Long userId);
}

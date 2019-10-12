package org.nure.julia.service;

import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;

public interface UserService {
    boolean addUser(final WebUserDto webUserDto);

    WebUserDto getUserInfo(Long userId);

    WebUserDto authorizeUser(final WebUserCredentialsDto webUserCredentialsDto);
}

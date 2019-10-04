package org.nure.julia.service;

import org.nure.julia.dto.FullUserDto;
import org.nure.julia.dto.WebUserDto;

public interface UserService {
    boolean addUser(final WebUserDto webUserDto);

    WebUserDto getUserInfo(Long userId);

    FullUserDto authorizeUser(final WebUserDto webUserDto);
}

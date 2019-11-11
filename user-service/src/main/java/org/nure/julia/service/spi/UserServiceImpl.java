package org.nure.julia.service.spi;

import org.modelmapper.ModelMapper;
import org.nure.julia.dto.UserHealthDto;
import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;
import org.nure.julia.entity.Device;
import org.nure.julia.entity.UserHealth;
import org.nure.julia.entity.WebUser;
import org.nure.julia.exceptions.MissingEmailOrPasswordException;
import org.nure.julia.exceptions.UserEmailExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.mappings.BasicMapper;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final String gatewayURL;
    private final BasicMapper<UserHealthDto, UserHealth> userHealthDtoUserHealthBasicMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           String gatewayURL, BasicMapper<UserHealthDto, UserHealth> userHealthDtoUserHealthBasicMapper) {

        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gatewayURL = gatewayURL;
        this.userHealthDtoUserHealthBasicMapper = userHealthDtoUserHealthBasicMapper;
    }

    @Override
    public boolean addUser(final WebUserDto webUserDto) {
        if (!userRepository.findByEmail(webUserDto.getEmail()).isPresent()) {
            WebUser user = modelMapper.map(webUserDto, WebUser.class);
            user.setCreationDate(new Date());
            userRepository.save(user);

            return nonNull(user.getId());
        } else {
            throw new UserEmailExistsException("User`s email is already in use");
        }
    }

    @Override
    public WebUserDto updateUserInfo(WebUserDto webUserDto, Long userId) {
        WebUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

        if (nonNull(webUserDto.getPhotoUrl()) ) {
            user.setPhotoUrl(webUserDto.getPhotoUrl());
        }

        if (nonNull(webUserDto.getUsername()) ) {
            user.setUsername(webUserDto.getUsername());
        }

        return modelMapper.map(userRepository.save(user), WebUserDto.class);
    }

    @Override
    public WebUserDto getUserInfo(Long userId) {
        WebUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

        return modelMapper.map(user, WebUserDto.class);
    }

    @Override
    public WebUserDto authorizeUser(WebUserCredentialsDto webUserCredentialsDto) {
        if (nonNull(webUserCredentialsDto.getEmail()) && nonNull(webUserCredentialsDto.getPassword())) {
            WebUser user = userRepository.findByEmailAndPassword(webUserCredentialsDto.getEmail(), webUserCredentialsDto.getPassword())
                    .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

            return modelMapper.map(user, WebUserDto.class);
        } else {
            throw new MissingEmailOrPasswordException("Email or password is missing");
        }
    }

    @Override
    public Map<String, List<UserHealthDto>> getUserHealthInfo(Long userId) {
        WebUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

        return user.getDevices()
                .stream()
                .collect(toMap(Device::getDeviceId, device -> device.getHealthStatuses()
                        .stream()
                        .map(userHealthDtoUserHealthBasicMapper::reversalMap)
                        .collect(Collectors.toList()))
                );
    }
}

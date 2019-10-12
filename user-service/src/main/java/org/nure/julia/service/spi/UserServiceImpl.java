package org.nure.julia.service.spi;

import org.modelmapper.ModelMapper;
import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;
import org.nure.julia.entity.WebUser;
import org.nure.julia.exceptions.MissingEmailOrPasswordException;
import org.nure.julia.exceptions.UserEmailExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
}

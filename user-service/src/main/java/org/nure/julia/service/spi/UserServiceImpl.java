package org.nure.julia.service.spi;

import org.modelmapper.ModelMapper;
import org.nure.julia.dto.FullUserDto;
import org.nure.julia.dto.SessionDto;
import org.nure.julia.dto.WebUserDto;
import org.nure.julia.entity.user.WebUser;
import org.nure.julia.exceptions.MissingEmailOrPasswordException;
import org.nure.julia.exceptions.SessionManagementException;
import org.nure.julia.exceptions.UserEmailExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.repository.UserRepository;
import org.nure.julia.service.UserAuthorizationService;
import org.nure.julia.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserAuthorizationService authorizationService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserAuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean addUser(final WebUserDto webUserDto) {
        if (!userRepository.findByEmail(webUserDto.getEmail()).isPresent()) {
            WebUser user = modelMapper.map(webUserDto, WebUser.class);
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
    public FullUserDto authorizeUser(WebUserDto webUserDto) {
        if (nonNull(webUserDto.getEmail()) && nonNull(webUserDto.getPassword())) {
            WebUser user = userRepository.findByEmailAndPassword(webUserDto.getEmail(), webUserDto.getPassword())
                    .orElseThrow(() -> new UserNotFoundException("User doesn`t exist"));

            SessionDto sessionDto = authorizationService.registerClaim(user)
                    .orElseThrow(() -> new SessionManagementException("Cannot register user session"));

            FullUserDto fullUserDto = new FullUserDto();
            fullUserDto.setWebUser(modelMapper.map(user, WebUserDto.class));
            fullUserDto.setSession(sessionDto);

            return fullUserDto;
        } else {
            throw new MissingEmailOrPasswordException("Email or password is missing");
        }
    }
}

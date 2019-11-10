package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.nure.julia.dto.UserHealthDto;
import org.nure.julia.dto.UserSessionDto;
import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;
import org.nure.julia.exceptions.MissingEmailOrPasswordException;
import org.nure.julia.exceptions.SessionManagementException;
import org.nure.julia.exceptions.UserEmailExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.service.UserAuthorizationService;
import org.nure.julia.service.UserService;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.nure.julia.web.WebControllerDefinitions.BASE_URL;
import static org.nure.julia.web.WebControllerDefinitions.USER_ID_PARAMETER;

@ApplicationController
@RequestMapping(BASE_URL)
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserAuthorizationService authorizationService;

    @Autowired
    public UserControllerImpl(UserService userService, UserAuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            MissingEmailOrPasswordException.class,
            SessionManagementException.class,
            UserEmailExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity createUser(WebUserDto userDto) {
        return userService.addUser(userDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            MissingEmailOrPasswordException.class,
            SessionManagementException.class,
            UserEmailExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity getUserInfo(@SessionAttribute(name = USER_ID_PARAMETER) Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            MissingEmailOrPasswordException.class,
            SessionManagementException.class,
            UserEmailExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity authorize(HttpServletRequest request, HttpServletResponse response,
                                                WebUserCredentialsDto webUserCredentialsDto) {

        WebUserDto webUserDto = userService.authorizeUser(webUserCredentialsDto);
        UserSessionDto userSessionDto = authorizationService.registerClaim()
                .orElseThrow(() -> new SessionManagementException("Cannot register session"));

        request.getSession().setAttribute("validationKey", userSessionDto.getValidationKey());
        request.getSession().setAttribute("userId", webUserDto.getId());
        response.addHeader("SecurityToken", userSessionDto.getToken());

        return ResponseEntity.ok(webUserDto);
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            MissingEmailOrPasswordException.class,
            SessionManagementException.class,
            UserEmailExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity getUserHealthData(@SessionAttribute(name = USER_ID_PARAMETER) Long userId) {
        return ResponseEntity.ok(userService.getUserHealthInfo(userId));
    }

    private ResponseEntity fallback(WebUserDto userDto) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(Long userId) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(HttpServletRequest request, HttpServletResponse response,
                                                WebUserCredentialsDto webUserCredentialsDto) {
        return this.defaultFallback();
    }
}

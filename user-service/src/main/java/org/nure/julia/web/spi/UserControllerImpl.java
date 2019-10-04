package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.nure.julia.dto.FullUserDto;
import org.nure.julia.dto.WebUserDto;
import org.nure.julia.service.UserService;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static org.nure.julia.web.WebControllerDefinitions.*;

@ApplicationController
@RequestMapping(BASE_URL)
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity createUser(@RequestBody WebUserDto userDto) {
        return userService.addUser(userDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<WebUserDto> getUserInfo(@RequestHeader(name = USER_ID_PARAMETER) Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<WebUserDto> authorize(HttpServletResponse response, @RequestBody WebUserDto userDto) {
        FullUserDto fullUserDto = userService.authorizeUser(userDto);
        response.addHeader("SecurityToken", fullUserDto.getSession().getToken());

        return ResponseEntity.ok(fullUserDto.getWebUser());
    }

    private ResponseEntity fallback(WebUserDto userDto) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(Long userId) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(HttpServletResponse response, WebUserDto userDto) {
        return this.defaultFallback();
    }
}

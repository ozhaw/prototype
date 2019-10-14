package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.nure.julia.dto.WebUserCredentialsDto;
import org.nure.julia.dto.WebUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.nure.julia.web.WebControllerDefinitions.*;

@FeignClient(name = "${spring.application.name}.UserController")
public interface UserController extends HystrixFallbackController {
    @PostMapping
    ResponseEntity createUser(@RequestBody final WebUserDto userDto);

    @GetMapping(USER_WITH_USER_ID_PARAMETER_URL)
    ResponseEntity<WebUserDto> getUserInfo(Long userId);

    @PostMapping(USER_AUTHORIZATION_URL)
    ResponseEntity<WebUserDto> authorize(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody WebUserCredentialsDto webUserCredentialsDto);
}

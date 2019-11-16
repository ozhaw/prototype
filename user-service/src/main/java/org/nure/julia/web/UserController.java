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
    ResponseEntity getUserInfo(@RequestHeader(name = USER_ID_PARAMETER) Long userId);

    @PatchMapping(USER_WITH_USER_ID_PARAMETER_URL)
    ResponseEntity updateUserInfo(@RequestHeader(name = USER_ID_PARAMETER) Long userId,
                                  @RequestBody final WebUserDto userDto);

    @PostMapping(USER_AUTHORIZATION_URL)
    ResponseEntity authorize(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody WebUserCredentialsDto webUserCredentialsDto);

    @GetMapping(USER_HEALTH_URL)
    ResponseEntity getUserHealthData(@RequestHeader(name = USER_ID_PARAMETER) Long userId);

    @GetMapping(USER_HEALTH_DEVICE_ID_URL)
    ResponseEntity getUserHealthDataByDeviceId(@RequestHeader(name = USER_ID_PARAMETER) Long userId,
                                               @PathVariable(name = DEVICE_ID_PARAMETER) String deviceId);
}

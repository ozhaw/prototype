package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.nure.julia.dto.WebUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

import static org.nure.julia.web.WebControllerDefinitions.USER_AUTHORIZATION_URL;
import static org.nure.julia.web.WebControllerDefinitions.USER_WITH_USER_ID_PARAMETER_URL;

@FeignClient(name = "user-service")
public interface UserController extends HystrixFallbackController {
    @PostMapping
    ResponseEntity createUser(final WebUserDto userDto);

    @GetMapping(USER_WITH_USER_ID_PARAMETER_URL)
    ResponseEntity<WebUserDto> getUserInfo(Long userId);

    @PostMapping(USER_AUTHORIZATION_URL)
    ResponseEntity<WebUserDto> authorize(HttpServletResponse response, final WebUserDto userDto);
}

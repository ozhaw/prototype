package org.nure.julia.web;

import org.nure.julia.web.dto.NotificationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.nure.julia.web.WebControllerDefinitions.TEST_URL;
import static org.nure.julia.web.WebControllerDefinitions.USER_ID_PARAM;

public interface NotificationController {

    @PostMapping(TEST_URL)
    ResponseEntity testNotification(@RequestHeader(USER_ID_PARAM) Long userId,
                                    @RequestBody NotificationRequestDto notificationRequestDto);
}

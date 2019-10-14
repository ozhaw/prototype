package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.DeviceController;
import org.nure.julia.web.dto.DeviceDto;
import org.nure.julia.web.exceptions.UniqueDeviceAlreadyExistsException;
import org.nure.julia.web.service.DeviceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static java.lang.Long.valueOf;
import static java.util.Objects.requireNonNull;
import static org.nure.julia.web.WebControllerDefinitions.BASE_URL;

@ApplicationController
@RequestMapping(BASE_URL)
public class DeviceControllerImpl implements DeviceController {
    private final DeviceService deviceService;

    public DeviceControllerImpl(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity addDevice(Long userId, final DeviceDto deviceDto) {
        return deviceService.addDevice(userId, deviceDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity<DeviceDto> getDeviceByDeviceId(String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceByDeviceId(deviceId));
    }

    @Override
    @HystrixCommand(commandKey = "default", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity<DeviceDto> getDeviceById(Long deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    private ResponseEntity fallback(HttpServletRequest httpServletRequest, DeviceDto f) {
        return this.defaultFallback();
    }

    private ResponseEntity<DeviceDto> fallback(Long f) {
        return this.defaultFallback();
    }

    private ResponseEntity<DeviceDto> fallback(String f) {
        return this.defaultFallback();
    }

}

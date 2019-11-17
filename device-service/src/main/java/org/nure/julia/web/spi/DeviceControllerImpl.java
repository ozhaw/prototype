package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.DeviceController;
import org.nure.julia.dto.DeviceDto;
import org.nure.julia.exceptions.DeviceNotFoundException;
import org.nure.julia.exceptions.UniqueDeviceAlreadyExistsException;
import org.nure.julia.exceptions.UserNotFoundException;
import org.nure.julia.service.DeviceInfoService;
import org.nure.julia.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Long.valueOf;
import static java.util.Objects.requireNonNull;
import static org.nure.julia.web.WebControllerDefinitions.BASE_URL;

@ApplicationController
@RequestMapping(BASE_URL)
public class DeviceControllerImpl implements DeviceController {
    private final DeviceService deviceService;
    private final DeviceInfoService deviceInfoService;

    public DeviceControllerImpl(DeviceService deviceService, DeviceInfoService deviceInfoService) {
        this.deviceService = deviceService;
        this.deviceInfoService = deviceInfoService;
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity addDevice(Long userId, final DeviceDto deviceDto) {
        return deviceService.addDevice(userId, deviceDto)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity getDeviceByDeviceId(String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceByDeviceId(deviceId));
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class
    })
    public ResponseEntity getDeviceById(Long deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class,
            DeviceNotFoundException.class
    })
    public ResponseEntity getDeviceInfoForDevice(Long deviceId) {
        return ResponseEntity.ok(deviceInfoService.getDeviceInfoForDevice(deviceId));
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class,
            DeviceNotFoundException.class
    })
    public ResponseEntity getDeviceInfo(Long userId) {
        return ResponseEntity.ok(deviceInfoService.getDeviceInfo(userId));
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            UniqueDeviceAlreadyExistsException.class,
            UserNotFoundException.class
    })
    public ResponseEntity getDevicesForUser(Long userId) {
        return ResponseEntity.ok(deviceService.getDevicesForUser(userId));
    }

    private ResponseEntity fallback(HttpServletRequest httpServletRequest, DeviceDto f) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(Long f) {
        return this.defaultFallback();
    }

    private ResponseEntity fallback(String f) {
        return this.defaultFallback();
    }

}

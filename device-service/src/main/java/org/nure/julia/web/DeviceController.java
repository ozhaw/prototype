package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.nure.julia.web.dto.DeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

import static org.nure.julia.web.WebControllerDefinitions.*;

@FeignClient(name = "device-service")
public interface DeviceController extends HystrixFallbackController {
    @PostMapping
    ResponseEntity addDevice(HttpServletRequest httpServletRequest, @RequestBody final DeviceDto deviceDto);

    @GetMapping(EXTERNAL_DEVICE_ID_PARAMETER_URL)
    ResponseEntity<DeviceDto> getDeviceByDeviceId(@PathVariable(name = DEVICE_ID_PARAMETER) String deviceId);

    @GetMapping(DEVICE_ID_PARAMETER_URL)
    ResponseEntity<DeviceDto> getDeviceById(@PathVariable(name = DEVICE_ID_PARAMETER) Long deviceId);
}

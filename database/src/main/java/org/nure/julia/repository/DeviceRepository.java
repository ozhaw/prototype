package org.nure.julia.repository;

import org.nure.julia.entity.Device;
import org.nure.julia.entity.WebUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeviceRepository extends CrudRepository<Device, Long> {
    Optional<Device> findByDeviceId(String deviceId);
}

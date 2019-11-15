package org.nure.julia.exceptions;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException() {}

    public DeviceNotFoundException(String msg) {
        super(msg);
    }

    public DeviceNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}

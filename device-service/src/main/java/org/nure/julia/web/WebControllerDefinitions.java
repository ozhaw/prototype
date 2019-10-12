package org.nure.julia.web;

public final class WebControllerDefinitions {
    public static final String BASE_URL = "/device/api/device";

    public static final String DEVICE_ID_PARAMETER = "deviceId";

    //DeviceController Definitions
    public static final String DEVICE_ID_PARAMETER_URL = "/{" + DEVICE_ID_PARAMETER + "}";
    public static final String EXTERNAL_DEVICE_ID_PARAMETER_URL = "/external/{" + DEVICE_ID_PARAMETER + "}";

    private WebControllerDefinitions() {}
}

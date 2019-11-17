package org.nure.julia.web;

public final class WebControllerDefinitions {
    public static final String BASE_URL = "/device/api/device";

    public static final String DEVICE_ID_PARAMETER = "deviceId";
    public static final String USER_ID_PARAMETER = "userId";

    //DeviceController Definitions
    public static final String DEVICE_ID_PARAMETER_URL = "/{" + DEVICE_ID_PARAMETER + "}";
    public static final String EXTERNAL_DEVICE_ID_PARAMETER_URL = "/external/{" + DEVICE_ID_PARAMETER + "}";

    public static final String INFO_DATA_URL = "/info";

    public static final String DEVICE_HEALTH_DATA_URL = DEVICE_ID_PARAMETER_URL + "/health";
    public static final String DEVICE_INFO_DATA_URL = DEVICE_ID_PARAMETER_URL + "/info";

    public static final String USER_DEVICES_URL = "/users/{" + USER_ID_PARAMETER + "}/devices";

    private WebControllerDefinitions() {}
}

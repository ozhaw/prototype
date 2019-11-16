package org.nure.julia.web;

public final class WebControllerDefinitions {
    public static final String BASE_URL = "/user/api/user";

    public static final String USER_ID_PARAMETER = "userId";
    public static final String DEVICE_ID_PARAMETER = "deviceId";

    //UserController Definitions
    public static final String USER_WITH_USER_ID_PARAMETER_URL = "/operations";
    public static final String USER_AUTHORIZATION_URL = "/authorization";

    public static final String USER_HEALTH_URL = "/health";
    public static final String USER_HEALTH_DEVICE_ID_URL = "/device/{" + DEVICE_ID_PARAMETER + "}/health";

    private WebControllerDefinitions() {}
}

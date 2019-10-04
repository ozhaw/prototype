package org.nure.julia.web;

public final class WebControllerDefinitions {
    public static final String BASE_URL = "/api/user";

    public static final String USER_ID_PARAMETER = "userId";

    //UserController Definitions
    public static final String USER_WITH_USER_ID_PARAMETER_URL = "/operations/{" + USER_ID_PARAMETER + "}";
    public static final String USER_AUTHORIZATION_URL = "/authorization";

    private WebControllerDefinitions() {}
}

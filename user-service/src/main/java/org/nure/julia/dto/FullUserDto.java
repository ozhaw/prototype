package org.nure.julia.dto;

public class FullUserDto {
    private WebUserDto webUser;
    private SessionDto session;

    public WebUserDto getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUserDto webUser) {
        this.webUser = webUser;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }
}

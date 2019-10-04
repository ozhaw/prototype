package org.nure.julia.dto;

import java.io.Serializable;
import java.util.Date;

public class SessionDto implements Serializable {
    private String token;
    private Date expirationDate;

    public SessionDto(String token, long lifeTime) {
        this.token = token;
        this.expirationDate = new Date(System.currentTimeMillis() + lifeTime);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

}

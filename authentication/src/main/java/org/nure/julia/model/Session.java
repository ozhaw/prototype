package org.nure.julia.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Session implements Serializable {
    private String token;
    private String validationKey;
    private Date expirationDate;

    public Session(String token, long lifeTime) {
        this.token = token;
        this.validationKey = UUID.randomUUID().toString();
        this.expirationDate = Date.from(new Date().toInstant().plusMillis(lifeTime));
    }

    public Session(String token, String validationKey, long lifeTime) {
        this.token = token;
        this.validationKey = validationKey;
        this.expirationDate = Date.from(new Date().toInstant().plusMillis(lifeTime));
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

    public String getValidationKey() {
        return validationKey;
    }
}

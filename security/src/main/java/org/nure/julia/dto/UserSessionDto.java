package org.nure.julia.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class UserSessionDto implements Serializable {
    private String token;
    private String validationKey;
    private Date expirationDate;

    public UserSessionDto() {
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

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setValidationKey(String validationKey) {
        this.validationKey = validationKey;
    }
}

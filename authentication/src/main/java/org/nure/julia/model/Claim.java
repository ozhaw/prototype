package org.nure.julia.model;

import java.io.Serializable;

public class Claim implements Serializable {
    private String identifier;
    private String claimKey;
    private Session session;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClaimKey() {
        return claimKey;
    }

    public void setClaimKey(String claimKey) {
        this.claimKey = claimKey;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

package org.nure.julia.dto;

public class ClaimIdentityDto {

    private String identifier;
    private String claimKey;

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
}

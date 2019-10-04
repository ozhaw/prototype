package org.nure.julia.entity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface KeyClaimable {
    String getClaimKey();

    default String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    default boolean validate(String key) {
        return key.equals(getClaimKey());
    }
}

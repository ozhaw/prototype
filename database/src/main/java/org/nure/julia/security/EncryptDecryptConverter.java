package org.nure.julia.security;

import javax.persistence.AttributeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptDecryptConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}

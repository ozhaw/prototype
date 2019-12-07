package org.nure.julia.service.spi;

import org.nure.julia.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

@Service
public class TokenEncryptService implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenEncryptService.class);
    private static final Random RANDOM = new Random();

    @Override
    public String requestToken() {
        LOGGER.debug("Generating new token");
        return encrypt(UUID.randomUUID().toString() + Long.toHexString(RANDOM.nextLong()));
    }

    private String encrypt(String toEncrypt) {
        String hash = "";
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] digest = hasher.digest(toEncrypt.getBytes(StandardCharsets.UTF_8));
            hash = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to get SHA256 algorithm", e);
        }
        return hash.substring(0, hash.length() / 2);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

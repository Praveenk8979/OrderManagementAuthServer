package com.ordermanagement.OrderManagementAndAuthServer.keys;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtKeys {

    private static final String SECRET = "89praveen79ChaUhan21Thakur32Shahab"; // must be 32+ chars
    private static final long ACCESS_EXPIRATION = 1000 * 60 * 15; // 15 min
    private static final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hr

    public SecretKey getSecretKey() {
        byte[] keyBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getExpiration() {
        return ACCESS_EXPIRATION;
    }

    public long getRefreshExpiration() {
        return REFRESH_EXPIRATION;
    }
}

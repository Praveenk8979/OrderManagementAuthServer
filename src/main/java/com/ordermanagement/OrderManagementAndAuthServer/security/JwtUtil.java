package com.ordermanagement.OrderManagementAndAuthServer.security;

import com.ordermanagement.OrderManagementAndAuthServer.keys.JwtKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {

    @Autowired
    private JwtKeys keys;

    public String generateAccessToken(String username) {
        return buildToken(username, keys.getExpiration());
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, keys.getRefreshExpiration());
    }

    private String buildToken(String subject, long exp) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(SignatureAlgorithm.HS256, keys.getSecret())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(keys.getSecret())
                .parseClaimsJws(token).getBody();
    }
}

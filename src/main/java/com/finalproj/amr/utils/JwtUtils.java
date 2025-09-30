package com.finalproj.amr.utils;

import com.finalproj.amr.jsonObject.UserJwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    byte[] secretBytes = "your-256-bit-secret-your-256-bit-secret".getBytes(); // must be 256-bit for HS256
    SecretKey key = Keys.hmacShaKeyFor(secretBytes);
    public String generateToken(UserJwt user) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 86400000; // 1 day

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("iat", nowMillis / 1000) // issued at in seconds
                .claim("exp", expMillis / 1000) // expiration in seconds
                .signWith(key)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public UserJwt getUserJwt(String token) {
        Claims claims = extractAllClaims(token);
        int id = claims.get("id", Integer.class);
        String username = claims.get("username", String.class);
        String email = claims.get("email", String.class);
        return new UserJwt(id, username,email);
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

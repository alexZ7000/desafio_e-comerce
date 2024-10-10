package com.example.comerce.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public final class JWTService {

    private final static String SECRET_KEY = "secret";

    public String generateToken(final String username) throws Exception {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("exp", System.currentTimeMillis() + 1000 * 60 * 60); // 1 hora
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) throws Exception {
        final String header = Base64.getUrlEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        final String payload = Base64.getUrlEncoder().encodeToString(new ObjectMapper().writeValueAsBytes(claims));

        final String signature = createSignature(header, payload);

        return header + "." + payload + "." + signature;
    }

    private String createSignature(final String header, final String payload) throws Exception {
        final String data = header + "." + payload;
        final byte[] hmacSha256 = hmacSha256(data);
        return Base64.getUrlEncoder().encodeToString(hmacSha256);
    }

    private byte[] hmacSha256(final String data) throws Exception {
        final javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(JWTService.SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(final String token) throws Exception {
        final String payload = token.split("\\.")[1];
        final Map<String, Object> claims = new ObjectMapper().readValue(Base64.getUrlDecoder().decode(payload), Map.class);
        return (String) claims.get("username");
    }

    public boolean validateToken(final String token, final String username) throws Exception {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token) && verifySignature(token));
    }

    private boolean isTokenExpired(final String token) throws Exception {
        final String payload = token.split("\\.")[1];
        final Map<String, Object> claims = new ObjectMapper().readValue(Base64.getUrlDecoder().decode(payload), Map.class);
        final long expiration = (Long) claims.get("exp");
        return new Date(expiration).before(new Date());
    }

    private boolean verifySignature(final String token) throws Exception {
        final String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        final String header = parts[0];
        final String payload = parts[1];
        final String signature = parts[2];

        final String expectedSignature = createSignature(header, payload);
        return signature.equals(expectedSignature);
    }
}

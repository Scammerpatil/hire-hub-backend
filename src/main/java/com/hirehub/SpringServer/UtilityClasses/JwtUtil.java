package com.hirehub.SpringServer.UtilityClasses;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Key key = "THISISTHESECRETKEYFORSPRINGSERVERAPPLICATION".getBytes().length >= 32 ?
            Keys.hmacShaKeyFor("THISISTHESECRETKEYFORSPRINGSERVERAPPLICATION".getBytes()) :
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(JwtParameter param) {
        long EXPIRATION = 1000 * 60 * 60; // 1 hour
        return Jwts.builder()
                .setSubject(param.getEmail())       // email as subject
                .claim("id", param.getId())         // add custom claims
                .claim("name", param.getName())
                .claim("role", param.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

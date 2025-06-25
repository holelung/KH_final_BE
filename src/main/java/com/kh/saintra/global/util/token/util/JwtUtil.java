package com.kh.saintra.global.util.token.util;

import java.util.Date;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secretKey;
    private SecretKey key;

    @PostConstruct
    public void init(){
        byte[] keyArr = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyArr);
    }

    public String getAccessToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (3600000L*24*5)))
                .signWith(key)
                .compact();
    }

    public String getRefreshToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (3600000L*24*7)))
                .signWith(key)
                .compact();
    }

    public Claims parseJwt(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}


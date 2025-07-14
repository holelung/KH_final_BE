package com.kh.saintra.global.util.token.util;

import java.util.Date;
import java.time.Duration;
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
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofSeconds(10).toMillis())) // 1시간
                .signWith(key)
                .compact();
    }

    public String getRefreshToken(String username){
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofDays(30).toMillis())) // 30일
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


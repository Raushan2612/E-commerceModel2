package com.cts.ppstores.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.ppstores.models.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtUtils {
	
    @Value("${JWT_SECRETE_KEY}")
    private String secreteJWTKey; 

    @Value("${JWT_EXPIRATION_TIME}")
    private long ExperationTime;
    
    private SecretKey key;

    @PostConstruct
    private void init(){
        byte[] keyBytes = secreteJWTKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("JWT secret key initialized.");
    }

    public String generateToken(User user){
        String username = user.getEmail();
        return generateToken(username);
    }

    public String generateToken(String username){
    	log.debug("Generating token for user: {}", username);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ExperationTime))
                .signWith(key)
                .compact();
        log.info("Token generated for username: {}", username);
        return token;
    }

    public String getUsernameFromToken(String token) {
        log.debug("Extracting username from token.");
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        boolean isExpired = expiration.before(new Date());
        log.debug("Token expiration check result: {}", isExpired);
        return isExpired;
    }
}
package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtService {

    private final JwtParser jwtParser;

    public JwtService(JwtParser jwtParser){
        this.jwtParser = jwtParser;
    }

    private Claims parseToken(String token){
        try {
            return jwtParser.parseClaimsJwt(token).getBody();
        }catch (Exception e){
            throw new RuntimeException("Invalid JWT tiken", e);
        }
    }

    public String getRoleFromToken(String token){
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }

    public Boolean validateToken(String token){
        try{
            jwtParser.parseClaimsJwt(token);
            return !isTokenExpired(token);
        }catch (JwtException e){
            throw new RuntimeException("Invalid Jwt token", e);
        }
    }

    public String getEmailFromToken(String token){
        Claims claims = parseToken(token);
        return claims.get("email", String.class);

    }

    public String getPasswordFromToken(String token){
        Claims claims = parseToken(token);
        return claims.get("password", String.class);

    }

    public boolean hasRole(String token, String role){
        String tokenRole = getRoleFromToken(token);
        return role.equalsIgnoreCase(tokenRole);
    }

    public boolean isTokenExpired(String token){
        Claims claims = parseToken(token);
        return claims.getExpiration().getTime() < System.currentTimeMillis();
    }
}

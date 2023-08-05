package com.example.myproject.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.myproject.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", user.getEmail());
    claims.put("created", new Date());

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
    }

}

package com.example.myproject.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class JwtFilter extends OncePerRequestFilter {

    @Value("${authorization.token.header.name}")
    private String authorizationHeader;

    @Value("${authorization.token.header.prefix}")
    private String tokenPrefix;

    @Value("${jwt.secret}")
    private String secretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(this.authorizationHeader);
        logger.info("Received Authorization Header: {}", authorizationHeader);

        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(this.tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(this.tokenPrefix, "");
        logger.info("Parsed token: {}", token);

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey.getBytes()).build().parseClaimsJws(token).getBody();
            logger.info("Claims from token: {}", claims);
            String email = claims.getSubject();

            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UserDetails userDetails = new User(email, "", roles);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, roles);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            logger.info("Set authentication to security context: {}", authenticationToken);
        } catch (Exception e) {
            logger.error("Error parsing token or setting authentication: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
package com.tarun.movieReviewApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private  String secretKey;

    private static final  long JWT_EXPIRATION_TIME =1000L * 60 * 60 * 24 * 30;//30 days

    public String generatedToken(String username){
        return  Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

    }
    public  String extractUsername(String token){
         return  extractClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        return  extractClaims(token).getExpiration().before(new Date());
    }

    public  boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

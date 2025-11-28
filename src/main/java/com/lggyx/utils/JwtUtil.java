package com.lggyx.utils;

import com.lggyx.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties props;

    public String generate(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + props.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS256, props.getSecret())
                .compact();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(props.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(props.getSecret()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
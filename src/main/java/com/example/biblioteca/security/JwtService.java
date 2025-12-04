package com.example.biblioteca.security;

import com.example.biblioteca.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "Rivaldo Aragon Biblioteca";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 4; // 4 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("rol", usuario.getRol().getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    public Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean esTokenValido(String token, Usuario usuario) {
        String email = extraerEmail(token);
        return email.equals(usuario.getEmail()) && !esTokenExpirado(token);
    }

    private boolean esTokenExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }
}

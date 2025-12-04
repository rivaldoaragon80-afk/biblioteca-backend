package com.example.biblioteca.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Component
public class JwtUtils {

    // =========================================================
    // CLAVE SECRETAs DESDE application.properties
    // =========================================================
    private final Key secretKey;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Tiempo de expiración: 24 horas
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // =========================================================
    // GENERAR TOKEN
    // =========================================================
    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // =========================================================
    // VALIDAR TOKEN
    // =========================================================
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================
    // OBTENER EMAIL
    // =========================================================
    public String obtenerEmail(String token) {
        return obtenerClaims(token).getSubject();
    }

    // =========================================================
    // OBTENER ROL
    // =========================================================
    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }

    // =========================================================
    // OBTENER TODOS LOS CLAIMS
    // =========================================================
    private Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


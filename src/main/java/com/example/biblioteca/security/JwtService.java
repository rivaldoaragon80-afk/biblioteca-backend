package com.example.biblioteca.security;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtils jwtUtils;

    public JwtService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generarToken(String email) {
        return jwtUtils.generarToken(email);
    }
}

package com.example.biblioteca.security;

import com.example.biblioteca.config.JwtUtils;
import com.example.biblioteca.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtUtils jwtUtils, UsuarioService usuarioService) {
        this.jwtUtils = jwtUtils;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Si no envía token, continuar sin autenticación
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Si el token no es válido, continuar
        if (!jwtUtils.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer datos del token
        String email = jwtUtils.obtenerEmail(token);
        String rol = jwtUtils.obtenerRol(token);

        if (email != null && rol != null) {
            // Crear autoridad del rol
            SimpleGrantedAuthority autoridad =
                    new SimpleGrantedAuthority("ROLE_" + rol);

            // Crear el token de autenticación
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.singleton(autoridad)
                    );

            // Guardar en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}

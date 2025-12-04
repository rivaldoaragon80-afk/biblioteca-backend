package com.example.biblioteca.security;

import com.example.biblioteca.dto.*;
import com.example.biblioteca.model.Rol;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.repository.RolRepository;
import com.example.biblioteca.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // =============================
    // LOGIN
    // =============================
    public LoginResponse login(LoginRequest request) {

        // Autentica credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Usuario desde BD
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());

        // Crear token JWT
        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().getNombre());

        // Construir response
        return new LoginResponse(token, usuario.getEmail(), usuario.getRol().getNombre());
    }

    // =============================
    // REGISTRO DE USUARIOS
    // =============================
    public UsuarioResponse registrar(RegistroUsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Buscar rol
        Rol rol = rolRepository.findByNombre(request.getRol());
        if (rol == null) {
            throw new RuntimeException("Rol no válido: " + request.getRol());
        }

        // Crear usuario
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setEmail(request.getEmail());
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevo.setRol(rol);

        usuarioRepository.save(nuevo);

        // Construir respuesta sin exponer la clave
        return new UsuarioResponse(
                nuevo.getId(),
                nuevo.getNombre(),
                nuevo.getEmail(),
                nuevo.getRol().getNombre(),
                nuevo.isActivo()
        );
    }
}

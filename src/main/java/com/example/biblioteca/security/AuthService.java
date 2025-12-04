package com.example.biblioteca.security;

import com.example.biblioteca.dto.LoginRequest;
import com.example.biblioteca.dto.LoginResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.model.Rol;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.repository.RolRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.example.biblioteca.config.JwtUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // =========================================================
    //                    REGISTRO DE USUARIO
    // =========================================================
    public Usuario registrarUsuario(RegistroUsuarioRequest request) {

        // Validar email duplicado
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }

        // Obtener rol
        Rol rol = rolRepository.findByNombre(request.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado."));

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    // =========================================================
    //                            LOGIN
    // =========================================================
    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas."));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas.");
        }

        // Generar JWT
        String token = jwtUtils.generarToken(usuario.getEmail(), usuario.getRol().getNombre());

        // Crear respuesta
        return new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }
}


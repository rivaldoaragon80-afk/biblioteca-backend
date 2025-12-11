package com.example.biblioteca.service.Impl;

import com.example.biblioteca.dto.LoginResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.model.Rol;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.repository.RolRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.example.biblioteca.security.JwtService;
import com.example.biblioteca.service.AuthService;
import com.example.exception.ApiException;
import com.example.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getEmail());
        
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            log.error("Email ya registrado: {}", request.getEmail());
            throw new ApiException("El email ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(true)
                .build();

        // Asignar rol
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> {
                    log.error("Rol no encontrado con ID: {}", request.getRolId());
                    return new RecursoNoEncontradoException("Rol no encontrado con ID: " + request.getRolId());
                });
        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente con ID: {}", usuarioGuardado.getId());
        return usuarioGuardado;
    }

    @Override
    public LoginResponse login(String email, String password) {
        log.info("Intento de login para usuario: {}", email);
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado: {}", email);
                    return new BadCredentialsException("Credenciales inválidas");
                });

        if (!usuario.isActivo()) {
            log.error("Usuario inactivo intentando login: {}", email);
            throw new ApiException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            log.error("Contraseña incorrecta para usuario: {}", email);
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String token = jwtService.generarToken(usuario.getEmail());
        log.info("Login exitoso para usuario: {}", email);
        return new LoginResponse(token, usuario.getEmail());
    }
}

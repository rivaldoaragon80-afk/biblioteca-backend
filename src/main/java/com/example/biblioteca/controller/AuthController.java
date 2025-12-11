package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LoginRequest;
import com.example.biblioteca.dto.LoginResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.dto.UsuarioResponse;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        Usuario usuario = authService.registrarUsuario(request);
        UsuarioResponse response = mapToResponse(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setActivo(usuario.isActivo());
        if (usuario.getRol() != null) {
            response.setRol(usuario.getRol().getNombre());
        }
        return response;
    }
}

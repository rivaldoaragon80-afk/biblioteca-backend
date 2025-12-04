package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LoginRequest;
import com.example.biblioteca.dto.LoginResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")  // Permite peticiones desde cualquier frontend (React, Angular, Vue, etc)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ============================================================
    //                        REGISTRO
    // ============================================================
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegistroUsuarioRequest request) {
        try {
            Usuario usuarioRegistrado = authService.registrarUsuario(request);
            return ResponseEntity.ok(usuarioRegistrado);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }

    // ============================================================
    //                          LOGIN
    // ============================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error en login: " + e.getMessage());
        }
    }
}


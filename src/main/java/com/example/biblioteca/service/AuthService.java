package com.example.biblioteca.service;

import com.example.biblioteca.dto.LoginResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.model.Usuario;

public interface AuthService {

    Usuario registrarUsuario(RegistroUsuarioRequest request);

    LoginResponse login(String email, String password);
}

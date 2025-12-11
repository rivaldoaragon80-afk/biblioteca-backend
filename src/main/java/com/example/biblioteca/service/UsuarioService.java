package com.example.biblioteca.service;

import com.example.biblioteca.model.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    Usuario actualizarUsuario(Long id, Usuario usuario);

    void eliminarUsuario(Long id);

    List<Usuario> listarUsuarios();

    Usuario obtenerUsuarioPorId(Long id);
}

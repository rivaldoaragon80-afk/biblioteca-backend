package com.example.biblioteca.service;

import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.model.Rol;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.repository.RolRepository;
import com.example.biblioteca.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================
    //       USADO SOLO POR AuthService — NO LLAMAR DIRECTO
    // ============================================================
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    //              BÚSQUEDA POR EMAIL
    // ============================================================
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // ============================================================
    //              OBTENER POR ID
    // ============================================================
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ============================================================
    //              LISTAR TODOS
    // ============================================================
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // ============================================================
    //              ACTUALIZAR USUARIO
    // ============================================================
    public Usuario actualizar(Long id, Usuario nuevosDatos) {

        Usuario usuario = obtenerPorId(id);

        usuario.setNombre(nuevosDatos.getNombre());

        // No permitir que cualquiera edite email
        usuario.setEmail(nuevosDatos.getEmail());

        return usuarioRepository.save(usuario);
    }

    // ============================================================
    //              CAMBIAR PASSWORD
    // ============================================================
    public Usuario cambiarPassword(Long id, String nuevaPassword) {
        Usuario usuario = obtenerPorId(id);
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    //              ASIGNAR NUEVO ROL
    // ============================================================
    public Usuario asignarRol(Long idUsuario, String nombreRol) {

        Usuario usuario = obtenerPorId(idUsuario);

        Rol rol = rolRepository.findByNombre(nombreRol);

        if (rol == null) {
            throw new RuntimeException("El rol no existe");
        }

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    //              DESACTIVAR USUARIO
    // ============================================================
    public Usuario desactivar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    //              ELIMINAR USUARIO
    // ============================================================
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No existe usuario con ID " + id);
        }
        usuarioRepository.deleteById(id);
    }
}


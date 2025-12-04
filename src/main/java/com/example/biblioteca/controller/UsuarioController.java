package com.example.biblioteca.controller;

import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ======================================================
    // OBTENER USUARIO POR ID
    // ======================================================
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // ======================================================
    // LISTAR TODOS LOS USUARIOS (solo admin)
    // ======================================================
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // ======================================================
    // ACTUALIZAR USUARIO (nombre, email)
    // ======================================================
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {

        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioActualizado));
    }

    // ======================================================
    // CAMBIAR CONTRASEÑA
    // ======================================================
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestParam String nuevaPassword) {

        return ResponseEntity.ok(usuarioService.cambiarPassword(id, nuevaPassword));
    }

    // ======================================================
    // ASIGNAR ROL (solo admin)
    // ======================================================
    @PutMapping("/{id}/asignar-rol")
    public ResponseEntity<Usuario> asignarRol(
            @PathVariable Long id,
            @RequestParam String rol) {

        return ResponseEntity.ok(usuarioService.asignarRol(id, rol));
    }

    // ======================================================
    // DESACTIVAR USUARIO
    // ======================================================
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }

    // ======================================================
    // ELIMINAR USUARIO
    // ======================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}

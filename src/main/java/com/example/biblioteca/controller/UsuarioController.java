package com.example.biblioteca.controller;

import com.example.biblioteca.dto.ErrorResponse;
import com.example.biblioteca.dto.RegistroUsuarioRequest;
import com.example.biblioteca.dto.UsuarioResponse;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ---------------------------
    // Registrar usuario (crea Usuario)
    // ---------------------------
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroUsuarioRequest request) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Obtener usuario por id
    // ---------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(id);
            return ResponseEntity.ok(toResponse(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Listar todos los usuarios
    // ---------------------------
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioResponse> responses = usuarios.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // ---------------------------
    // Actualizar usuario (nombre/email)
    // ---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody RegistroUsuarioRequest request) {

        try {
            // Convertimos RegistroUsuarioRequest a Usuario parcial para actualizar
            Usuario cambios = new Usuario();
            cambios.setNombre(request.getNombre());
            cambios.setEmail(request.getEmail());
            // NOTA: No tocamos password aquí (usar endpoint cambiar-password)

            Usuario actualizado = usuarioService.actualizarUsuario(id, cambios);
            return ResponseEntity.ok(toResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Cambiar contraseña
    // ---------------------------
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestParam String nuevaPassword) {
        try {
            Usuario actualizado = usuarioService.cambiarPassword(id, nuevaPassword);
            return ResponseEntity.ok(toResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Asignar rol
    // ---------------------------
    @PutMapping("/{id}/asignar-rol")
    public ResponseEntity<?> asignarRol(
            @PathVariable Long id,
            @RequestParam String rol) {
        try {
            Usuario actualizado = usuarioService.asignarRol(id, rol);
            return ResponseEntity.ok(toResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Desactivar usuario
    // ---------------------------
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            Usuario actualizado = usuarioService.desactivarUsuario(id);
            return ResponseEntity.ok(toResponse(actualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Eliminar usuario
    // ---------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    // ---------------------------
    // Helper: mapear entidad a DTO de respuesta
    // ---------------------------
    private UsuarioResponse toResponse(Usuario u) {
        if (u == null) return null;
        String rolNombre = (u.getRol() != null) ? u.getRol().getNombre() : null;

        UsuarioResponse res = new UsuarioResponse();
        res.setId(u.getId());
        res.setNombre(u.getNombre());
        res.setEmail(u.getEmail());
        res.setRol(rolNombre);
        return res;
    }
}


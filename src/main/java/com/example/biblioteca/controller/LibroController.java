package com.example.biblioteca.controller;

import com.example.biblioteca.dto.ErrorResponse;
import com.example.biblioteca.dto.LibroRequest;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // Crear libro
    @PostMapping
    public ResponseEntity<?> crearLibro(@RequestBody LibroRequest request) {
        try {
            LibroResponse response = libroService.crearLibro(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al crear libro: " + e.getMessage())
            );
        }
    }

    // Listar todos los libros
    @GetMapping
    public ResponseEntity<List<LibroResponse>> listarLibros() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    // Obtener libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerLibro(@PathVariable Long id) {
        try {
            LibroResponse response = libroService.obtenerLibro(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Libro no encontrado")
            );
        }
    }

    // Actualizar libro
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(
            @PathVariable Long id,
            @RequestBody LibroRequest request
    ) {
        try {
            LibroResponse response = libroService.actualizarLibro(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al actualizar libro: " + e.getMessage())
            );
        }
    }

    // Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.ok().body("Libro eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al eliminar libro")
            );
        }
    }
}

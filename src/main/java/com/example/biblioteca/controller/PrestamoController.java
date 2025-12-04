package com.example.biblioteca.controller;

import com.example.biblioteca.dto.ErrorResponse;
import com.example.biblioteca.dto.PrestamoRequest;
import com.example.biblioteca.dto.PrestamoResponse;
import com.example.biblioteca.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // Crear préstamo
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequest request) {
        try {
            PrestamoResponse response = prestamoService.crearPrestamo(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al crear préstamo: " + e.getMessage())
            );
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> listarPrestamos() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    // Obtener préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPrestamo(@PathVariable Long id) {
        try {
            PrestamoResponse response = prestamoService.obtenerPrestamo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Préstamo no encontrado")
            );
        }
    }

    // Actualizar préstamo
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrestamo(
            @PathVariable Long id,
            @RequestBody PrestamoRequest request
    ) {
        try {
            PrestamoResponse response = prestamoService.actualizarPrestamo(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al actualizar préstamo: " + e.getMessage())
            );
        }
    }

    // Eliminar préstamo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable Long id) {
        try {
            prestamoService.eliminarPrestamo(id);
            return ResponseEntity.ok().body("Préstamo eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Error al eliminar préstamo")
            );
        }
    }
}


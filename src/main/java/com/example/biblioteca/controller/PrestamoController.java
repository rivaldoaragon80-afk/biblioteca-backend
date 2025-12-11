package com.example.biblioteca.controller;

import com.example.biblioteca.dto.PrestamoRequest;
import com.example.biblioteca.dto.PrestamoResponse;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
@CrossOrigin("*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<PrestamoResponse> crearPrestamo(@RequestBody PrestamoRequest request) {
        Prestamo p = prestamoService.crearPrestamo(request.getUsuarioId(), request.getLibroId());

        PrestamoResponse response = new PrestamoResponse(
                p.getId(),
                p.getUsuario().getNombre(),
                p.getLibro().getTitulo(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstado().name()
        );
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/devolver/{id}")
    public ResponseEntity<PrestamoResponse> devolver(@PathVariable Long id) {
        Prestamo p = prestamoService.devolverPrestamo(id);
        PrestamoResponse response = new PrestamoResponse(
                p.getId(),
                p.getUsuario().getNombre(),
                p.getLibro().getTitulo(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstado().name()
        );
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPrestamoPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Prestamo>> prestamosPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(prestamoService.buscarPorUsuario(idUsuario));
    }
}

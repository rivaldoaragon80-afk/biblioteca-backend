package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LibroRequest;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.model.Libro;
import com.example.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest request) {
        Libro libro = mapToEntity(request);
        Libro libroCreado = libroService.registrar(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(libroCreado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        Libro libro = mapToEntity(request);
        Libro libroActualizado = libroService.actualizar(id, libro);
        return ResponseEntity.ok(mapToResponse(libroActualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping
    public ResponseEntity<List<LibroResponse>> listar() {
        List<LibroResponse> libros = libroService.listar()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(libros);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtener(@PathVariable Long id) {
        Libro libro = libroService.obtenerPorId(id);
        return ResponseEntity.ok(mapToResponse(libro));
    }

    private Libro mapToEntity(LibroRequest request) {
        return Libro.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .anio(request.getAnio())
                .stock(request.getStock())
                .isbn(request.getIsbn())
                .descripcion(request.getDescripcion())
                .build();
    }

    private LibroResponse mapToResponse(Libro libro) {
        LibroResponse response = new LibroResponse();
        response.setId(libro.getId());
        response.setTitulo(libro.getTitulo());
        response.setAutor(libro.getAutor());
        response.setAnio(libro.getAnio());
        response.setStock(libro.getStock());
        return response;
    }
}

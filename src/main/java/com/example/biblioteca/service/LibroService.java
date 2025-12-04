package com.example.biblioteca.service;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro registrar(Libro libro) {

        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new RuntimeException("El título es obligatorio");
        }

        if (libro.getAutor() == null || libro.getAutor().isEmpty()) {
            throw new RuntimeException("El autor es obligatorio");
        }

        if (libro.getStock() < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }

        return libroRepository.save(libro);
    }

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public Libro actualizar(Long id, Libro libroActualizado) {

        Libro libro = obtenerPorId(id);

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setStock(libroActualizado.getStock());

        return libroRepository.save(libro);
    }

    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }
}


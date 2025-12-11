package com.example.biblioteca.service;

import com.example.biblioteca.model.Libro;
import java.util.List;

public interface LibroService {

    Libro registrar(Libro libro);

    Libro actualizar(Long id, Libro libro);

    void eliminar(Long id);

    List<Libro> listar();

    Libro obtenerPorId(Long id);
}

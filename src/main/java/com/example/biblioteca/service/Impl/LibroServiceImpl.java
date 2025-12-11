package com.example.biblioteca.service.Impl;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.service.LibroService;
import com.example.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    @Override
    @Transactional
    public Libro registrar(Libro libro) {
        log.info("Registrando nuevo libro: {}", libro.getTitulo());
        Libro libroGuardado = libroRepository.save(libro);
        log.info("Libro registrado exitosamente con ID: {}", libroGuardado.getId());
        return libroGuardado;
    }

    @Override
    public List<Libro> listar() {
        log.debug("Listando todos los libros");
        return libroRepository.findAll();
    }

    @Override
    public Libro obtenerPorId(Long id) {
        log.debug("Buscando libro con ID: {}", id);
        return libroRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Libro no encontrado con ID: {}", id);
                    return new RecursoNoEncontradoException("Libro no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional
    public Libro actualizar(Long id, Libro libroActualizado) {
        log.info("Actualizando libro con ID: {}", id);
        Libro libroExistente = obtenerPorId(id);

        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setAnio(libroActualizado.getAnio());
        libroExistente.setStock(libroActualizado.getStock());
        
        if (libroActualizado.getIsbn() != null) {
            libroExistente.setIsbn(libroActualizado.getIsbn());
        }
        if (libroActualizado.getDescripcion() != null) {
            libroExistente.setDescripcion(libroActualizado.getDescripcion());
        }

        Libro libroGuardado = libroRepository.save(libroExistente);
        log.info("Libro actualizado exitosamente con ID: {}", id);
        return libroGuardado;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando libro con ID: {}", id);
        Libro libro = obtenerPorId(id);
        libroRepository.delete(libro);
        log.info("Libro eliminado exitosamente con ID: {}", id);
    }
}

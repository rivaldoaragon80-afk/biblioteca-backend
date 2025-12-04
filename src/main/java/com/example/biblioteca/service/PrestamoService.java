package com.example.biblioteca.service;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.PrestamoRepository;
import com.example.biblioteca.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(
            PrestamoRepository prestamoRepository,
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Prestamo registrarPrestamo(Long idUsuario, Long idLibro) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getStock() <= 0) {
            throw new RuntimeException("No hay stock disponible");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado("PRESTADO");

        libro.setStock(libro.getStock() - 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    public Prestamo devolverLibro(Long idPrestamo) {

        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getEstado().equals("DEVUELTO")) {
            throw new RuntimeException("Este libro ya fue devuelto");
        }

        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucion(LocalDate.now());

        Libro libro = prestamo.getLibro();
        libro.setStock(libro.getStock() + 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }
}

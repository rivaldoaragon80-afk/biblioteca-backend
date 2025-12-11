package com.example.biblioteca.service.Impl;

import com.example.biblioteca.model.*;
import com.example.biblioteca.repository.*;
import com.example.biblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepository,
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Prestamo crearPrestamo(Long usuarioId, Long libroId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getStock() <= 0) {
            throw new RuntimeException("No hay stock disponible");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        libro.setStock(libro.getStock() - 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo devolverPrestamo(Long prestamoId) {

        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new RuntimeException("Este libro ya fue devuelto");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDate.now());

        Libro libro = prestamo.getLibro();
        libro.setStock(libro.getStock() + 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    }

    @Override
    public List<Prestamo> buscarPorUsuario(Long idUsuario) {
        return prestamoRepository.findByUsuarioId(idUsuario);
    }
}

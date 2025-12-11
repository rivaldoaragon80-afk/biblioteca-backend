package com.example.biblioteca.service;

import com.example.biblioteca.model.Prestamo;
import java.util.List;

public interface PrestamoService {

    Prestamo crearPrestamo(Long usuarioId, Long libroId);

    Prestamo devolverPrestamo(Long prestamoId);

    List<Prestamo> listarPrestamos();

    Prestamo obtenerPrestamoPorId(Long id);

    List<Prestamo> buscarPorUsuario(Long idUsuario);
}

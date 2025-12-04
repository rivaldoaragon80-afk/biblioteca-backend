package com.example.biblioteca.repository;

import com.example.biblioteca.model.Prestamo;
import com.example.biblioteca.model.Usuario;
import com.example.biblioteca.model.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuario(Usuario usuario);

    List<Prestamo> findByEstado(EstadoPrestamo estado);
}

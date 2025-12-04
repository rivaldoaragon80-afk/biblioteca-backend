package com.example.biblioteca.dto;

import java.time.LocalDate;

public class PrestamoResponse {

    private Long id;
    private Long idUsuario;
    private Long idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado;

    public PrestamoResponse() {}

    public PrestamoResponse(Long id, Long idUsuario, Long idLibro, LocalDate fechaPrestamo,
                            LocalDate fechaDevolucion, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdLibro() { return idLibro; }
    public void setIdLibro(Long idLibro) { this.idLibro = idLibro; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}


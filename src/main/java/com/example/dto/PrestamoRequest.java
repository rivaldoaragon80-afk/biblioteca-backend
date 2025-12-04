package com.example.biblioteca.dto;

public class PrestamoRequest {

    private Long idUsuario;
    private Long idLibro;

    public PrestamoRequest() {}

    public PrestamoRequest(Long idUsuario, Long idLibro) {
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdLibro() { return idLibro; }
    public void setIdLibro(Long idLibro) { this.idLibro = idLibro; }
}


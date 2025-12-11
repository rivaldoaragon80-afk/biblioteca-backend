package com.example.biblioteca.dto;

public class PrestamoRequest {

    private Long usuarioId;
    private Long libroId;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
}

package com.example.biblioteca.dto;

public class LoginResponse {
    private String mensaje;
    private String email;

    public LoginResponse(String mensaje, String email) {
        this.mensaje = mensaje;
        this.email = email;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

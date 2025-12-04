package com.example.biblioteca.dto;

public class LibroRequest {

    private String titulo;
    private String autor;
    private int stock;

    public LibroRequest() {}

    public LibroRequest(String titulo, String autor, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}


package com.example.biblioteca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private int stock;

    public Libro() {}

    public Libro(String titulo, String autor, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }
}

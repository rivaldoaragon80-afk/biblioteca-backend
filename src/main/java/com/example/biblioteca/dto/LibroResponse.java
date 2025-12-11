package com.example.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponse {
    private Long id;
    private String titulo;
    private String autor;
    private Integer anio;
    private Integer stock;
}


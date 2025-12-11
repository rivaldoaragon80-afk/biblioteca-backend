package com.example.biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 1, max = 100, message = "El autor debe tener entre 1 y 100 caracteres")
    private String autor;
    
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1000, message = "El año debe ser mayor o igual a 1000")
    @Max(value = 2100, message = "El año debe ser menor o igual a 2100")
    private Integer anio;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    private String isbn;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
}

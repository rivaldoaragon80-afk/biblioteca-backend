package com.example.exception;

public class ApiException extends RuntimeException {
    
    public ApiException(String mensaje) {
        super(mensaje);
    }
    
    public ApiException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

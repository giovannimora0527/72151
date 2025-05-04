package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author MiguelRayo_853345
 **/
@Data 
public class PrestamoRq {
    private Integer usuarioId;             // Requerido cuando se crea el prestamo
    private Integer libroId;               // Requerido cuando se crea el prestamo
    private LocalDateTime fechaPrestamo;   // Requerido cuando se crea el prestamo
    private LocalDateTime fechaDevolucion; // Requerido cuando se crea el prestamo
    private LocalDateTime fechaEntrega;    // Null cuando se crea el prestamo   
}

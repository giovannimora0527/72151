package com.uniminuto.biblioteca.model;

import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrestamoRq {
    
    private Integer idUsuario;
    private Integer idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;
    private LocalDateTime fechaEntrega;
}

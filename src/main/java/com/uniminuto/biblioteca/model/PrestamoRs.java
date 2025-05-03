package com.uniminuto.biblioteca.model;

import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrestamoRs {
    private Integer idPrestamo;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idLibro;
    private String tituloLibro;
    private LocalDateTime fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDateTime fechaEntrega;
    private EstadoPrestamo estado;
    private String message;
}


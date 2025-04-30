package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrestamoDto {
    private Integer idPrestamo;
    private String usuario;
    private String libro;
    private LocalDateTime fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaEntrega;
    private String estado;
}
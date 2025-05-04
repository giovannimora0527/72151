package com.uniminuto.biblioteca.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PrestamoRq {

    private Integer usuarioId;
    private Integer libroId;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private LocalDateTime fechaEntrega;
    private String estado;

}

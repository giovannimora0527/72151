package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoRq {
    private Integer idUsuario;
    private Integer idLibro;
    private LocalDate fechaDevolucion;
}
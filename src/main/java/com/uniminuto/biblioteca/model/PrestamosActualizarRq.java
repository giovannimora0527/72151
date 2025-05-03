package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamosActualizarRq {
    private Integer idPrestamo;
    private LocalDate fechaEntrega;
}
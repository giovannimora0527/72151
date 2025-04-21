package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author ewilliams
 */
@Data
public class AutorRq {
    private Integer autorId;
    private Integer nacionalidadId;
    private LocalDate fechaNacimiento;
    private String nombre;

}


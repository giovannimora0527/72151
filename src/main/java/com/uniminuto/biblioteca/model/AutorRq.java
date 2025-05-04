package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

/**
 * @author MiguelRayo_853345
 **/
@Data 
public class AutorRq {
    private String nombre;
    private Integer nacionalidadId;
    private LocalDate fechaNacimiento;
}

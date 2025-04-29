package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AutorRq 
{
    private Integer autorId;
    private String nombre;
    private Integer nacionalidadId;
    private LocalDate fechaNacimiento;
}

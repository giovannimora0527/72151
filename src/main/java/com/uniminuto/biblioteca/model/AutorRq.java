package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AutorRq {
    private String nombre;
    private String  nacionalidad;
    private LocalDate fechaNacimiento;
}

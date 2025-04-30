package com.uniminuto.biblioteca.model;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AutorRs {
    private Integer autorId;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String message; 
}

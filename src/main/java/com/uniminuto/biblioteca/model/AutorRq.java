package com.uniminuto.biblioteca.model;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AutorRq {
    private String nombre;
     private Nacionalidad nacionalidad; 
    private LocalDate fechaNacimiento;
}

package com.uniminuto.biblioteca.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AutorRq {

    private LocalDate fechaNacimiento;
    private Integer nacionalidadId;
    private String nombre;
}
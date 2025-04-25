package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * Clase que representa la solicitud para crear o actualizar un autor.
 * Contiene los campos requeridos desde el cliente.
 * 
 * Autor: lmora
 */
@Data
public class AutorRq {
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
}

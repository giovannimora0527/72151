package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * Clase que representa la solicitud para crear o actualizar un préstamo
 */
@Data
public class PrestamoRq {
    /**
     * ID del usuario que realiza el préstamo
     */
    private Integer idUsuario;
    
    /**
     * ID del libro a prestar
     */
    private Integer idLibro;
    
    /**
     * Fecha en que se realiza el préstamo
     * Se establece automáticamente por el sistema
     */
    private LocalDate fechaPrestamo;
    
    /**
     * Fecha límite para la devolución del libro
     * Debe ser al menos un día posterior a la fecha actual
     */
    private LocalDate fechaDevolucion;
    
    /**
     * Fecha de entrega efectiva del libro
     * Es nula hasta que el usuario devuelve el libro
     */
    private LocalDate fechaEntrega;
    
    /**
     * Estado del préstamo (PRESTADO, VENCIDO, DEVUELTO)
     * Se gestiona automáticamente por el sistema
     */
    private String estado;
}
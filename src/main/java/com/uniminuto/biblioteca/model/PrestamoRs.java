package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * Clase que representa la respuesta de un préstamo
 */
@Data
public class PrestamoRs {
    /**
     * Identificador único del préstamo
     */
    private Long idPrestamo;
    
    /**
     * Nombre del usuario que realizó el préstamo
     */
    private String nombreUsuario;
    
    /**
     * Título del libro prestado
     */
    private String tituloLibro;
    
    /**
     * Fecha en que se realizó el préstamo
     */
    private LocalDate fechaPrestamo;
    
    /**
     * Fecha límite para la devolución del libro
     */
    private LocalDate fechaDevolucion;
    
    /**
     * Fecha de entrega efectiva del libro
     * Es nula si el libro aún no ha sido devuelto
     */
    private LocalDate fechaEntrega;
    
    /**
     * Estado del préstamo (PRESTADO, VENCIDO, DEVUELTO)
     */
    private String estado;
    
    /**
     * Mensaje informativo para la respuesta
     */
    private String message;
}
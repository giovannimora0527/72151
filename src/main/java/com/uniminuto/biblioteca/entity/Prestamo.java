package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único del libro (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    /** Autor del libro (clave foránea que referencia a la entidad Autor). */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    /** Autor del libro (clave foránea que referencia a la entidad Autor). */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    /**
     * Fecha de registro del usuario (se asigna automáticamente).
     */
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    /**
     * Estado actual del préstamo (PRESTADO, DEVUELTO, VENCIDO).
     * Mapeado al ENUM de la base de datos.
     */
    @Enumerated(EnumType.STRING) // Mapea el enum de Java a String en la BD
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado; // Campo que usa el enum

    public enum EstadoPrestamo {
        PRESTADO,
        DEVUELTO,
        VENCIDO }
    
}
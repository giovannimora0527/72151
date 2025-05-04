package com.uniminuto.biblioteca.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un préstamo de un libro en la biblioteca.
 * Contiene información sobre las fechas, estado, usuario y libro asociados al préstamo.
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo {

    /**
     * Identificador único del préstamo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer prestamoId;

    /**
     * Fecha y hora en que se realizó el préstamo.
     */
    @Column(name = "fecha_prestamo", nullable = false, length = 100)
    private LocalDateTime fechaPrestamo;

    /**
     * Fecha y hora límite para la devolución del libro.
     */
    @Column(name = "fecha_devolucion", nullable = false, length = 100)
    private LocalDateTime fechaDevolucion;

    /**
     * Fecha y hora en que se entregó el libro (puede ser nula si no se ha entregado).
     */
    @Column(name = "fecha_entrega", nullable = true, length = 100)
    private LocalDateTime fechaEntrega;

    /**
     * Estado actual del préstamo (por ejemplo: "activo", "devuelto", etc).
     */
    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    /**
     * Usuario que realizó el préstamo.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Libro que fue prestado.
     */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

}

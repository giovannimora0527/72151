package com.uniminuto.biblioteca.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer prestamoId;

    @Column(name = "fecha_prestamo", nullable = false, length = 100)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false, length = 100)
    private LocalDateTime fechaDevolucion;

    @Column(name = "fecha_entrega", nullable = true, length = 100)
    private LocalDateTime fechaEntrega;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

}

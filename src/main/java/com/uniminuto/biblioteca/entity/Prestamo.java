package com.uniminuto.biblioteca.entity;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prestamos")
public class Prestamo {

    public enum EstadoPrestamo {
        PRESTADO, VENCIDO, DEVUELTO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(name = "fecha_prestamo", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "ENUM('PRESTADO', 'VENCIDO', 'DEVUELTO')")
    private EstadoPrestamo estado;

    //  Lógica para calcular el estado automáticamente
    @PrePersist
    @PreUpdate
    @PostLoad 
    public void calcularEstado() {
        if (fechaEntrega != null) {
            estado = EstadoPrestamo.DEVUELTO;
        } else if (LocalDate.now().isAfter(fechaDevolucion)) {
            estado = EstadoPrestamo.VENCIDO;
        } else {
            estado = EstadoPrestamo.PRESTADO;
        }
    }
}
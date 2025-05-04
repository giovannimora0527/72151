package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
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
 * @author MiguelRayo_853345
 **/
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único del prestamo (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    /** Id del usuario que toma prestado un libro */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    /** Id del libro prestado */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;
    
    /** Fecha de prestamo del libro*/
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;
    
    /** Fecha de devolución pactada del libro*/
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDateTime fechaDevolucion;
    
    /** Fecha de entrega del libro*/
    @Column(name = "fecha_entrega", nullable = true)
    private LocalDateTime fechaEntrega;
    
    /**  
     * Estado del prestamo
     * Mapea el ENUM('PRESTADO','DEVUELTO','VENCIDO')  
     * Se almacena como texto en la BD y por defecto será PRESTADO
     */
    @Enumerated(EnumType.STRING)
    @Column(
      name = "estado", 
      nullable = false, 
      columnDefinition = "ENUM('PRESTADO','DEVUELTO','VENCIDO') DEFAULT 'PRESTADO'"
    )
    private EstadoPrestamo estado = EstadoPrestamo.PRESTADO;
}
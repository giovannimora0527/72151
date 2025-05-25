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
@Table(name = "multas")
public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único de la multa (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private Integer idMulta;

    /** Id del usuario que toma prestado un libro */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    /** Id del libro prestado */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;
    
    /** Id del prestamo */
    @ManyToOne
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;
    
    /**
     * Concepto de la multa.
     */
    @Column(name = "concepto", length = 255)
    private String concepto;
    
    /**
     * Monto de la multa
     */
    @Column(name = "monto", nullable = false)
    private Integer monto;
    
    /**
     * Fecha de la multa
     */
    @Column(name = "fecha_multa", nullable = false)
    private LocalDateTime fechaMulta;
    
    /**
     * Fecha de pago de la multa
     */
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
    
    /**  
     * Estado de la multa
     * Mapea el ENUM('PENDIENTE','PAGADO')  
     * Se almacena como texto en la BD y por defecto será PENDIENTE
     */
    @Enumerated(EnumType.STRING)
    @Column(
      name = "estado", 
      nullable = false, 
      columnDefinition = "ENUM('PENDIENTE','PAGADO') DEFAULT 'PENDIENTE'"
    )
    private EstadoMulta estado = EstadoMulta.PENDIENTE;
}
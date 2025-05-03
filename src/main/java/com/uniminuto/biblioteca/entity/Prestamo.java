package com.uniminuto.biblioteca.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Usuario;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author santiago
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 
     * Identificador único del préstamo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    /**
     * Usuario que realizó el préstamo.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    /**
     * Libro prestado.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
    private Libro libro;

    /**
     * Fecha en que se realizó el préstamo.
     */
    @Column(name = "fecha_prestamo")
    private LocalDateTime fechaPrestamo;

    /**
     * Fecha límite de devolución del libro.
     */
    
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    /**
     * Estado del préstamo: PRESTADO, DEVUELTO o VENCIDO.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPrestamo estado;
    

    public enum EstadoPrestamo {
        PRESTADO,
        DEVUELTO,
        VENCIDO
    }
    
    /**
 * Fecha en que el libro fue realmente entregado.
 */
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
@Column(name = "fecha_entrega")
private LocalDateTime fechaEntrega;

}


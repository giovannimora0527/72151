
package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa una deuda generada a partir de un préstamo vencido.
 * 
 * @author Santiago
 */
@Data
@Entity
@Table(name = "deudas")
public class Deuda implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la deuda.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deuda")
    private Integer idDeuda;

    /**
     * Préstamo asociado a la deuda.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prestamo", referencedColumnName = "id_prestamo", unique = true)
    private Prestamo prestamo;

    /**
     * Valor total de la deuda (incluyendo multa si aplica).
     */
    @Column(name = "valor_deuda", nullable = false)
    private BigDecimal valorDeuda;

    /**
     * Valor fijo de la multa en caso de préstamo vencido.
     */
    @Column(name = "multa_fija", nullable = false)
    private BigDecimal multaFija = new BigDecimal("20000.00");

    /**
     * Estado de pago de la deuda: Cancelado o No Cancelado.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago = EstadoPago.NO_CANCELADO;

    /**
     * Fecha en la que se pagó la deuda.
     */
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    /**
     * Método de pago usado: Efectivo, Nequi o Débito.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    /**
     * Usuario asociado a la deuda.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Fecha en la que se generó la deuda.
     */
    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;

    /**
     * Fecha límite para el pago de la deuda.
     */
    @Column(name = "fecha_limite_pago", nullable = false)
    private LocalDate fechaLimitePago;

    /**
     * Estado activo o inactivo de la deuda.
     */
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
    
    
    /**
     * Enum para los métodos de pago.
     */
    public enum MetodoPago {
        EFECTIVO,
        NEQUI,
        DÉBITO
    }

    /**
     * Enum para el estado de pago de la deuda.
     */
    public enum EstadoPago {
        CANCELADO,
        NO_CANCELADO
    }
}

package com.uniminuto.biblioteca.model;

import com.uniminuto.biblioteca.entity.Deuda;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response con los datos de una deuda registrada o consultada.
 * Incluye datos relacionados al préstamo, usuario y libro para mejor contexto.
 * 
 * @author Santiago
 */
@Data
public class DeudaRs {

    private Integer idDeuda;

    private Integer idPrestamo;

    private Integer idUsuario;
    private String nombreUsuario;

    private Integer idLibro;
    private String tituloLibro;

    private BigDecimal valorDeuda;
    private BigDecimal multaFija;

    private Deuda.EstadoPago estadoPago;
    private LocalDateTime fechaPago;
    private Deuda.MetodoPago metodoPago;

    private LocalDate fechaGeneracion;
    private LocalDate fechaLimitePago;

    private Boolean estado; // Activa o inactiva

    private String message;

}

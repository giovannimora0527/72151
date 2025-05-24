package com.uniminuto.biblioteca.model;

import lombok.Data;


/**
 * Request para registrar una deuda.
 * Solo requiere el idPrestamo, porque el resto (valorDeuda, multa, fechas, estado) se calcula o asigna en backend.
 * 
 * @author Santiago
 */
@Data
public class DeudaRq {

    private Integer idPrestamo;

}

package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.model.ActualizarDeudaRq;
import com.uniminuto.biblioteca.model.DeudaRq;
import com.uniminuto.biblioteca.model.DeudaRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

/**
 * Interfaz para el manejo de lógica de negocio de deudas.
 * 
 * @author Santiago
 */
public interface DeudaService {

    /**
     * Obtiene el listado completo de deudas.
     * @return 
     */
    List<Deuda> obtenerListadoDeudas();

    /**
     * Obtiene una deuda específica por su ID.
     * @param id
     * @return 
     * @throws org.apache.coyote.BadRequestException
     */
    Deuda obtenerDeudaPorId(Integer id) throws BadRequestException;

    /**
     * Guarda una nueva deuda.
     * @param deuda
     * @return 
     * @throws org.apache.coyote.BadRequestException
     */
    DeudaRs guardarDeuda(DeudaRq deuda) throws BadRequestException;

    /**
     * Actualiza una deuda existente.
     * @param rq
     * @return 
     * @throws org.apache.coyote.BadRequestException
     */
    RespuestaGenericaRs actualizarDeuda(ActualizarDeudaRq rq) throws BadRequestException;

}


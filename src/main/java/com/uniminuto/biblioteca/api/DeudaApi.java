package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.DeudaRq;
import com.uniminuto.biblioteca.model.DeudaRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.model.ActualizarDeudaRq;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API para la gestión de deudas.
 * 
 * @author Santiago
 */
@CrossOrigin(origins = "*")
@RequestMapping("/deuda")
public interface DeudaApi {

    /**
     * Listar todas las deudas registradas.
     *
     * @return Lista de deudas.
     * @throws BadRequestException en caso de error.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Deuda>> listarDeudas() throws BadRequestException;

    /**
     * Obtener una deuda específica por su ID.
     *
     * @param deudaId ID de la deuda.
     * @return Deuda encontrada.
     * @throws BadRequestException si no se encuentra.
     */
    @RequestMapping(value = "/obtener",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Deuda> obtenerDeudaPorId(@RequestParam Integer deudaId) throws BadRequestException;

    /**
     * Registrar una nueva deuda.
     *
     * @param deudaRq Objeto con la información de la deuda.
     * @return Objeto DeudaRs con información extendida.
     * @throws BadRequestException si ocurre un error al guardar.
     */
    @RequestMapping(value = "/guardar",
            consumes = {"application/json"},
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<DeudaRs> guardarDeuda(@RequestBody DeudaRq deudaRq) throws BadRequestException;

    /**
     * Actualizar los datos de una deuda existente.
     *
     * @param actualizarDeudaRq
     * @return Respuesta genérica con mensaje de confirmación.
     * @throws BadRequestException si ocurre un error.
     */
    @RequestMapping(value = "/actualizar",
        consumes = {"application/json"},
        produces = {"application/json"},
        method = RequestMethod.POST)
ResponseEntity<RespuestaGenericaRs> actualizarDeuda(@RequestBody ActualizarDeudaRq actualizarDeudaRq) throws BadRequestException;
}

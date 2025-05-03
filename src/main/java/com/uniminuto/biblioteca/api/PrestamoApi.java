package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.PrestamoDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamosActualizarRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    /**
     * Método para listar todos los préstamos registrados.
     * @return Lista de préstamos.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(
        value = "/listar", 
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.GET
    )
    ResponseEntity<List<PrestamoDto>> listarPrestamos() throws BadRequestException;

    
    /**
     * Método para crear préstamo en la bd.
     * @param PrestamoRq entrada.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(
        value = "/crear",
        method = RequestMethod.POST,
        produces = "application/json",
        consumes = "application/json"
    )
    ResponseEntity<RespuestaGenericaRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) 
        throws BadRequestException;
        

    /**
     * Método para actualizar préstamo en la bd.
     * @param PrestamosActualizarRq entrada.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(
        value = "/actualizar",
        method = RequestMethod.POST,
        produces = "application/json",
        consumes = "application/json"
    )
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(
        @RequestBody PrestamosActualizarRq prestamosActualizarRq
    ) throws BadRequestException;
}
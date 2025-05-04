package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.PrestamoRq;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author MiguelRayo_853345
 **/
@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {
    /**
     * Metodo para listar los prestamos registrados en bd.
     *
     * @return Lista de prestamos.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos()
            throws BadRequestException;
    
     /**
     * Metodo para listar los prestamos por id registrados en bd.
     *
     * @return Lista de prestamos.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> listarPrestamoPorId(@RequestParam Integer prestamoIds)
            throws BadRequestException;
    
    /**
     * Metodo para guardar los prestamos en bd.
     *
     * @return Respuesta generica.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/guardar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> guardarPrestamo(@RequestBody PrestamoRq prestamo)
            throws BadRequestException;
    
    /**
     * Metodo para actualizar los prestamos en bd.
     *
     * @return Respuesta generica.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo)
            throws BadRequestException;
    
}
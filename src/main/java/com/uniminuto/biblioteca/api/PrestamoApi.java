package com.uniminuto.biblioteca.api;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.coyote.BadRequestException;
/**
 *
 * @author santiago
 */
@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    /**
     * Método para listar los préstamos registrados en la base de datos.
     *
     * @return Lista de préstamos.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos()
            throws BadRequestException;

    /**
     * Método para obtener un préstamo por su ID.
     *
     * @param prestamoId ID del préstamo.
     * @return Objeto de tipo Prestamo.
     * @throws BadRequestException Excepción si no se encuentra el préstamo.
     */
    @RequestMapping(value = "/listar-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> listarPrestamoPorId(@RequestParam Integer prestamoId)
            throws BadRequestException;

    /**
     * Método para guardar un nuevo préstamo.
     *
     * @param prestamoRq Datos del préstamo a guardar.
     * @return Respuesta con el estado de la operación.
     * @throws BadRequestException Excepción si los datos son incorrectos.
     */
    @RequestMapping(value = "/guardar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> guardarPrestamo(@RequestBody PrestamoRq prestamoRq)
            throws BadRequestException;

    /**
     * Método para actualizar un préstamo existente.
     *
     * @param prestamo Objeto Prestamo con los datos a actualizar.
     * @return Respuesta genérica con el estado de la operación.
     * @throws BadRequestException Excepción si el préstamo no existe.
     */
    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo)
            throws BadRequestException;
}



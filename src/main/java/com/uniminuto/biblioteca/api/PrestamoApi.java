package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Interfaz que define la API para la gestión de préstamos en la biblioteca.
 * Proporciona métodos para listar, crear y actualizar préstamos.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
@RequestMapping("/prestamo") // Ruta base para los endpoints de préstamo
public interface PrestamoApi {

    /**
     * Lista todos los préstamos registrados en la base de datos.
     *
     * @return ResponseEntity con la lista de préstamos.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param prestamoId Identificador del préstamo.
     * @return ResponseEntity con el préstamo encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> obtenerPrestamoPorId(@RequestParam Integer prestamoId) throws BadRequestException;

    /**
     * Crea un nuevo préstamo en la base de datos.
     *
     * @param prestamoRq Objeto de solicitud con los datos del préstamo a crear.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/guardar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza la información de un préstamo existente.
     *
     * @param prestamo Objeto Prestamo con los datos actualizados.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo) throws BadRequestException;
}

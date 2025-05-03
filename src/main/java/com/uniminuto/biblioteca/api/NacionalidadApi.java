package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Interfaz que define la API para la gestión de nacionalidades en la biblioteca.
 * Proporciona métodos para listar nacionalidades.
 */
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
@RequestMapping("/nacionalidad") // Ruta base para los endpoints de nacionalidad
public interface NacionalidadApi {

    /**
     * Lista todas las nacionalidades registradas en la base de datos.
     *
     * @return ResponseEntity con la lista de nacionalidades.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Nacionalidad>> listarNacionalidades()
            throws BadRequestException;
}

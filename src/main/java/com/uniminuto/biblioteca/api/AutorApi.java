package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {
    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores()
            throws BadRequestException;


     /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds)
            throws BadRequestException;


    @RequestMapping(value = "/guardar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<AutorRs> guardarAutor(@RequestBody AutorRq autorRq)
            throws BadRequestException;

    @RequestMapping(value = "/actualizar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarAutor(@RequestBody Autor autor)
            throws BadRequestException;
}

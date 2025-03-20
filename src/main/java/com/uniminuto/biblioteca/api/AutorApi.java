package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.TestRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorId(@RequestParam Integer autorId)
            throws BadRequestException;
    
}
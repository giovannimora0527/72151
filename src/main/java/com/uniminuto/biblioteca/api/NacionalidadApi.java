package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/nacionalidad")
public interface NacionalidadApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Nacionalidad>> listarNacionalidad()
            throws BadRequestException;
}

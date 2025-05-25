package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Multa;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.MultaRq;
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
@RequestMapping("/multa")
public interface MultaApi {
    /**
     * Metodo para listar las multas registradas en bd.
     *
     * @return Lista de multas.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Multa>> listarMultas()
            throws BadRequestException;
    
     /**
     * Metodo para listar las multas por id registradas en bd.
     *
     * @return Lista de multas.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-multa-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Multa> listarMultaPorId(@RequestParam Integer multaIds)
            throws BadRequestException;
    
    /**
     * Metodo para actualizar las multas en bd.
     *
     * @return Respuesta generica.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar-multa",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarMulta(@RequestBody Multa multa)
            throws BadRequestException;
}
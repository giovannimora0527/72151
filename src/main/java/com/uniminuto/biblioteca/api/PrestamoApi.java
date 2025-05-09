package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/prestamos")
public interface PrestamoApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamo()
            throws BadRequestException;    
        
    @RequestMapping(value = "/crear-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearPrestamo(@RequestBody PrestamoRq PrestamoRq)
            throws BadRequestException;
    
    @RequestMapping(value = "/actualizar-prestamo/{id}",
             produces = {"application/json"},
             consumes = {"application/json"},
             method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(
            @PathVariable("id") Integer idPrestamo,
            @RequestBody Map<String, String> fechaEntregaBody
            )
             throws BadRequestException;

    @RequestMapping(value = "/{id}", // Mantener el ID en la URL para DELETE
                    produces = {"application/json"},
                    method = RequestMethod.DELETE) // Usamos DELETE
    ResponseEntity<RespuestaGenericaRs> archivarPrestamo(@PathVariable("id") Integer idPrestamo)
         throws BadRequestException;
}
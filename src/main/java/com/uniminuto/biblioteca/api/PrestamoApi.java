package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

    @RequestMapping(value = "/listar-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> obtenerPrestamoPorId(@RequestParam Integer prestamoId) throws BadRequestException;

    @RequestMapping(value = "/guardar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException;

    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo) throws BadRequestException;
}

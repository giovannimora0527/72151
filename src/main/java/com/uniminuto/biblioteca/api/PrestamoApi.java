package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    /**
     * Método para listar todos los préstamos registrados.
     * @return Lista de préstamos.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(
        value = "/listar", 
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.GET
    )
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;
}
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamos")
public interface PrestamoApi {
    
    /**
     * Lista todos los préstamos registrados
     * @return Lista de préstamos
     * @throws BadRequestException si ocurre un error
     */
    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamos() throws BadRequestException;
    
    /**
     * Crea un nuevo préstamo
     * @param prestamoRq Datos del préstamo a crear
     * @return Información del préstamo creado
     * @throws BadRequestException si ocurre un error
     */
    @RequestMapping(
            value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException;
    
    /**
     * Actualiza un préstamo existente
     * @param id ID del préstamo a actualizar
     * @param prestamoRq Datos del préstamo a actualizar
     * @return Información del préstamo actualizado
     * @throws BadRequestException si ocurre un error
     */
    @RequestMapping(
            value = "/actualizar/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<PrestamoRs> actualizarPrestamo(
            @PathVariable("id") Long id,
            @RequestBody PrestamoRq prestamoRq) throws BadRequestException;
    
    /**
     * Obtiene la lista de libros disponibles para préstamo
     * @return Lista de libros disponibles
     * @throws BadRequestException si ocurre un error
     */
    @RequestMapping(
            value = "/librosDisponibles",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibrosDisponibles() throws BadRequestException;
}
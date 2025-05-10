/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author camel
 */
@RequestMapping("/prestamos")
@CrossOrigin(origins = "*")
public interface PrestamoApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Prestamo>> listarPrestamos()
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param prestam
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> guardarPrestamo(@RequestBody Prestamo prestam)
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param prestamo
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> entregarPrestamo(@RequestBody Prestamo prestamo)
            throws BadRequestException;
}

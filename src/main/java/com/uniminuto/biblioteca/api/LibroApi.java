package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;

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
@RequestMapping("/libro")
public interface LibroApi {

        /**
         * Metodo para listar los autores registrados en bd.
         *
         * @return Lista de autores.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/listar", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<List<Libro>> listarLibros()
                        throws BadRequestException;

        /**
         * Metodo para listar los autores registrados en bd.
         *
         * @param libroId Id del libro.
         * @return Lista de autores.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/obtener-libro-id", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
                        throws BadRequestException;

        /**
         * Metodo para listar los libros de los autores registrados en bd.
         *
         * @param autorId Id del libro.
         * @return Lista de libros de autores.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/obtener-libro-autor", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<List<Libro>> obtenerLibroPorAutor(@RequestParam Integer autorId)
                        throws BadRequestException;

        /**
         * Metodo para listar los libros de los autores registrados en bd.
         *
         * @param titulo Id del libro.
         * @return Lista de libros de autores.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/obtener-libro-titulo", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<Optional<Libro>> obtenerLibroPorTitulo(@RequestParam String titulo)
                        throws BadRequestException;
}

package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los endpoints para la gestión de autores.
 * Incluye métodos para listar, crear y actualizar autores.
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
        @RequestMapping(value = "/listar", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<List<Autor>> listarAutores()
                        throws BadRequestException;

        /**
         * Metodo para listar los autores registrados en bd.
         *
         * @param nacionalidad nacionalidad del autor.
         * @return Lista de autores.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/listar-nacionalidad", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
                        @RequestParam String nacionalidad)
                        throws BadRequestException;

        /**
         * Metodo para obtener un autor por su ID.
         *
         * @param autorIds identificador del autor.
         * @return Autor.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/listar-autor-id", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.GET)
        ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds)
                        throws BadRequestException;

        /**
         * Metodo para crear un nuevo autor.
         *
         * @param autor datos del autor a crear.
         * @return Autor creado.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/crear", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<RespuestaGenericaRs> crearAutor(@RequestBody AutorRq autor)
                        throws BadRequestException;

        /**
         * Metodo para actualizar un autor existente.
         *
         * @param autor datos del autor a actualizar.
         * @return Autor actualizado.
         * @throws BadRequestException excepcion.
         */
        @RequestMapping(value = "/actualizar", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<RespuestaGenericaRs> actualizarAutor(@RequestBody Autor autor)
                        throws BadRequestException;
}
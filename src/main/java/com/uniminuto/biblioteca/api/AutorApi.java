package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Interfaz que define la API para la gestión de autores en la biblioteca.
 * Proporciona métodos para listar, crear y actualizar autores.
 * 
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Lista todos los autores registrados en la base de datos.
     *
     * @return ResponseEntity con la lista de autores.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores()
            throws BadRequestException;
    
    /**
     * Lista los autores filtrados por nacionalidad.
     *
     * @param nacionalidad Nacionalidad del autor a filtrar.
     * @return ResponseEntity con la lista de autores que cumplen el filtro.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar-nacionalidad",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
     @RequestParam String nacionalidad)
            throws BadRequestException;
    
    /**
     * Obtiene un autor por su identificador.
     *
     * @param autorIds Identificador del autor.
     * @return ResponseEntity con el autor encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds)
            throws BadRequestException;

    /**
     * Crea un nuevo autor en la base de datos.
     *
     * @param AutorRq Objeto de solicitud con los datos del autor a crear.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/guardar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearAutor(@RequestBody AutorRq AutorRq)
            throws BadRequestException;

    /**
     * Actualiza la información de un autor existente.
     *
     * @param autor Objeto Autor con los datos actualizados.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @RequestMapping(value = "/actualizar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarAutor(@RequestBody Autor autor)
            throws BadRequestException;

}

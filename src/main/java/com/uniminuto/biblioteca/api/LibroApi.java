package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
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
     * Metodo para listar los libros registrados en bd.
     *
<<<<<<< HEAD
     * @return Lista de libros.
=======
     * @return Lista de libros registrados.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException;
<<<<<<< HEAD
    
     /**
     * Metodo para listar los libros por id registrados en bd.
=======

    /**
     * Metodo para listar los autores registrados en bd.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
     *
     * @param libroId Id del libro.
     * @return Obtiene el libro por Id.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
            throws BadRequestException;
    
<<<<<<< HEAD
     /**
     * Metodo para listar los libros por id del autor registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorIdAutor(@RequestParam Integer autor)
            throws BadRequestException;
    
     /**
     * Metodo para listar los libros por nombre del autor registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
=======
    /**
     * Metodo para obtener los libros dado un autor.
     *
     * @param autorId Id del autor.
     * @return Lista de libros del autor.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorAutor(@RequestParam Integer autorId)
            throws BadRequestException;
    
    /**
     * Metodo para listar los libros dado un nombre.
     *
     * @param nombreLibro Nombre del libro.
     * @return Libri que cumpla el criterio.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-nombre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
<<<<<<< HEAD
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo)
            throws BadRequestException;
    
     /**
     * Metodo para listar los libros por fecha de publicación registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-fecha-publicacion",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(@RequestParam Integer anioPublicacionInicio,
            @RequestParam Integer anioPublicacionFin) throws BadRequestException;
=======
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String nombreLibro)
            throws BadRequestException;
    
    /**
     * Metodo para listar los libros dado un rango de fecha.
     *
     * @param anioIni
     * @param anioFin
     * @return Lista de libros.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-anio-publicacion",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(
            @RequestParam Integer anioIni,
            @RequestParam Integer anioFin)
            throws BadRequestException;
    
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
}

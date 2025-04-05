package com.uniminuto.biblioteca.api;

import java.util.List;
import com.uniminuto.biblioteca.entity.Libro;
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
@RequestMapping("/libros")
public interface LibroApi {
    /**
     * Metodo para listar los libros registrados en bd.
     *
<<<<<<< HEAD
     * @return Lista de libros.
=======
     * @return Lista de libros registrados.
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>>listarLibros()
            throws BadRequestException;
<<<<<<< HEAD
    
    
    @RequestMapping(value = "/listar-libros-id",
=======

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Obtiene el libro por Id.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id",
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> listarLibrosId(@RequestParam Integer id_libro)
            throws BadRequestException;
<<<<<<< HEAD
        
    @RequestMapping(value = "/listar-libros-autorid",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity <List<Libro>> listarLibrosAutorId(@RequestParam Integer id_autor)
            throws BadRequestException;
        
    @RequestMapping(value = "/listar-libros-nombre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> listarLibrosNombre(@RequestParam String titulo)
            throws BadRequestException;

    //Se
    @RequestMapping(value = "/listar-libros-fecha", //ruta endpoint
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)  //usa el metodo de HTTP
    ResponseEntity <List<Libro>> listarLibrosAnionRango(@RequestParam Integer inicioAnio, @RequestParam Integer finAnio)  
    //devuelve una lista de objetos libros,@RequestParam pra recibir dos parametros en rango
            throws BadRequestException;
}
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
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-nombre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
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
    
}
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579

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
     * @return Lista de libros.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>>listarLibros()
            throws BadRequestException;
    
    
    @RequestMapping(value = "/listar-libros-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> listarLibrosId(@RequestParam Integer id_libro)
            throws BadRequestException;
        
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
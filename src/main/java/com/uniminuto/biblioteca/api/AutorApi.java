package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
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
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores()
            throws BadRequestException;
   
    /**
    * Método para listar las nacionalidades desde los autores.
    *
    * @return Lista de nacionalidades.
    * @throws BadRequestException excepción en caso de error.
    */
    @RequestMapping(
       value = "/listarnacionalidad",
       method = RequestMethod.GET,
       produces = { "application/json" }
    )
    ResponseEntity<List<Nacionalidad>> listarNacionalidad() 
            throws BadRequestException;
    
    
    @RequestMapping(
    value = "/listar-por-nacionalidad",
    method = RequestMethod.GET,
    produces = {"application/json"}
    )
    ResponseEntity<List<Autor>> listarAutoresPorNacionalidad(@RequestParam Integer nacionalidadId)
            throws BadRequestException;

    
     /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param autorId
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorId)
            throws BadRequestException;
    
    /**
    * Guarda un autor nuevo.
    * @param autor autor a guardar.
    * @return respuesta del servicio.
    * @throws BadRequestException excepción en caso de error.
    */
   @RequestMapping(value = "/guardar-autor",
           produces = {"application/json"},
           consumes = {"application/json"},
           method = RequestMethod.POST)
   ResponseEntity<AutorRs> guardarAutor(@RequestBody AutorRq autor)
           throws BadRequestException;
   
   

   /**
    * Actualiza un autor.
    * @param autor autor a actualizar.
    * @return respuesta del servicio.
    * @throws BadRequestException excepción en caso de error.
    */
   @RequestMapping(value = "/actualizar-autor",
           produces = {"application/json"},
           consumes = {"application/json"},
           method = RequestMethod.POST)
   ResponseEntity<AutorRs> actualizarAutor(@RequestBody AutorRq autor)
           throws BadRequestException;

}


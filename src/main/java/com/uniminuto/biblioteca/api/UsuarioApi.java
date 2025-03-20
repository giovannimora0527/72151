package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
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
@RequestMapping("/usuarios")
public interface UsuarioApi {
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
    ResponseEntity<List<Usuario>>listarUsuarios()
            throws BadRequestException;
    
    
    @RequestMapping(value = "/listar-usuarios-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> listarUsuariosId(@RequestParam Integer id_usuario)
            throws BadRequestException;
    
        
    @RequestMapping(value = "/listar-usuarios-correo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> listarUsuariosCorreo(@RequestParam String correo)
            throws BadRequestException;
}
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
 * @author 853345_MiguelRayo
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario") 
public interface UsuarioApi {

    /**
     * Metodo para listar todos los usuarios registrados en bd.
     *
     * @return Lista de usuarios.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
    
    
    /**
     * Metodo para listar los usuarios por correo registrados en bd.
     *
     * @return Lista de usuarios.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-usuario-correo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarioPorCorreo(@RequestParam String correo)
            throws BadRequestException;
    
}

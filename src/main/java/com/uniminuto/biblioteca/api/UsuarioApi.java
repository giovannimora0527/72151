package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
=======
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestBody;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
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
=======
/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
<<<<<<< HEAD
    
    
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
    
=======

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param correo correo a buscar.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/buscar-por-correo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> buscarUsuarioPorEmail(
            @RequestParam String correo)
            throws BadRequestException;

    @RequestMapping(value = "/guardar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> guardarUsuario(@RequestBody UsuarioRq usuario)
            throws BadRequestException;
    
    @RequestMapping(value = "/actualizar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> actualizarUsuario(@RequestBody UsuarioRq usuario)
            throws BadRequestException;

>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
}

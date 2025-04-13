package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
=======
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestBody;
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
<<<<<<< HEAD
@RequestMapping("/usuarios")
public interface UsuarioApi {
    /**
     * Metodo para listar los libros registrados en bd.
     *
     * @return Lista de libros.
=======
@RequestMapping("/usuario")
public interface UsuarioApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
<<<<<<< HEAD
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
=======
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;

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
    ResponseEntity<RespuestaGenericaRs> actualizarUsuario(@RequestBody Usuario usuario)
            throws BadRequestException;

<<<<<<< HEAD
}
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
=======
}
>>>>>>> desarrollo

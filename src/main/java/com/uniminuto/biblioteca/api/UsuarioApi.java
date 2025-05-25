package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
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
@RequestMapping("/usuario")
public interface UsuarioApi {

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
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
    
    /**
     * Metodo para listar los usuarios sin multas ACTIVAS/PENDIENTES en BD.
     *
     * @return Lista de usuarios sin multas.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-usuarios-sin-multas-activas",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> obtenerUsuariosSinMultas()
            throws BadRequestException;
    
    /**
     * Metodo para listar los usuarios por id.
     *
     * @param id correo a buscar.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-por-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> buscarUsuarioPorId(@RequestParam Integer usuarioId)
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
    ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String correo)
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

}
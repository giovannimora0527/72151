package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
=======
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
<<<<<<< HEAD
 * @author 853345_MiguelRayo
 */
public interface UsuarioService {
    List<Usuario> obtenerListadoUsuarios() throws BadRequestException;
    
    List<Usuario> obtenerListadoUsuarioPorCorreo(String correo) throws BadRequestException;
=======
 * @author lmora
 */
public interface UsuarioService {
    
    /**
     * Servicio para listar todos los usuarios del sistema.
     * @return Lista de usuarios registrados.
     * @throws BadRequestException Excepcion.
     */
    List<Usuario> listarTodo() throws BadRequestException;
    
    /**
     * Busca un usuario dado un email.
     * @param correo email a buscar.
     * @return Usuario.
     * @throws BadRequestException excepcion.
     */
    Usuario buscarPorCorreo(String correo) throws BadRequestException;
    
    
    UsuarioRs guardarUsuario(UsuarioRq usuario) throws BadRequestException;
    
    UsuarioRs actualizarUsuario(UsuarioRq usuario) throws BadRequestException;
    
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
}

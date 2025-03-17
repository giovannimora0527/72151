package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author 853345_MiguelRayo
 */
public interface UsuarioService {
    List<Usuario> obtenerListadoUsuarios() throws BadRequestException;
    
    List<Usuario> obtenerListadoUsuarioPorCorreo(String correo) throws BadRequestException;
}

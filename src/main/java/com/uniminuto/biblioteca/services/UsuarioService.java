package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;

import java.util.List;
import org.apache.coyote.BadRequestException;
import java.util.Optional;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    List<Usuario> obtenerListadoUsuarios();

    Optional<Usuario> findUserByEmail(String correo) throws BadRequestException;
}

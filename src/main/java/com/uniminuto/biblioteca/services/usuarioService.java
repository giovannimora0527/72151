package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;

/**
 *
 * @author lmora
 */
public interface usuarioService {
    List<Usuario> obtenerListadoDeUsuarios();
    
    Usuario obtenerUsuariosPorId(Integer id_usuario);
    
    Usuario obtenerUsuariosCorreo(String correo);
   
}

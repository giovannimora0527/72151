package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 
 *
 * @author 853345_MiguelRayo
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerListadoUsuarios()
            throws BadRequestException {
        
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        if (usuarios.isEmpty()){
            throw new BadRequestException("No se encontraron usuarios registrados");
        }
        
        return usuarios;
    }

}

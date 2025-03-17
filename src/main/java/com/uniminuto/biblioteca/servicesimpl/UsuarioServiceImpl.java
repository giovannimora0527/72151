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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 *
 * @author 853345_MiguelRayo
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Expresión regular para validar un correo electrónico básico
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public List<Usuario> obtenerListadoUsuarios()
            throws BadRequestException {
        
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        if (usuarios.isEmpty()){
            throw new BadRequestException("No se encontraron usuarios registrados");
        }
        
        return usuarios;
    }
    
    @Override
    public List<Usuario> obtenerListadoUsuarioPorCorreo(String correo)
            throws BadRequestException {
        this.usuarioRepository.findByCorreo(correo).forEach(elem -> {
            System.out.println("Nombre Usuario => " + elem.getNombre());
        });
        
        // Validación de formato de correo
        if (correo == null || correo.isEmpty()) {
            throw new BadRequestException("El correo no puede estar vacío.");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(correo);
        if (!matcher.matches()) {
            throw new BadRequestException("Formato de correo inválido.");
        }
        
        List<Usuario> listaUsuarios = this.usuarioRepository.findByCorreo(correo);
        
        if (listaUsuarios.isEmpty()) {
            throw new BadRequestException("No existen usuarios con ese correo.");
        }
        
        return listaUsuarios;
    }

}

package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;


/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public List<Usuario> obtenerListadoUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findUserByEmail(String correo) {

        if (!isValidEmail(correo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico no es válido: " + correo);
        }

        Optional<Usuario> usuarios = usuarioRepository.findByCorreo(correo);

        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró el usuario con el correo: " + correo);
        }
        
        return usuarios;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}

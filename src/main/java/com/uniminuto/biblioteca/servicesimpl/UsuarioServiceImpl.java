package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
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
=======
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

<<<<<<< HEAD
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
=======
    /**
     * Patron para validar email.
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Regex para validacion de email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodo() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorCorreo(String correo) throws BadRequestException {
        if (correo == null || correo.isBlank()) {
            throw new BadRequestException("El correo: " + correo + ", no cumple "
                    + "la validación para ser un correo valido.");
        }

        boolean isValidoEmail = this.validarCorreo(correo);
        if (!isValidoEmail) {
            throw new BadRequestException("El correo no es valido.");
        }

        Optional<Usuario> optUsuario = this.usuarioRepository
                .findByCorreo(correo);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No hay registros de usuarios "
                    + "registrados con el correo ingresado.");
        }
        return optUsuario.get();
    }

    /**
     * 
     * @param correo
     * @return 
     */
    public boolean validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    @Override
    public UsuarioRs guardarUsuario(UsuarioRq usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository.findByNombre(usuario.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya se encuentra registrado. Intente de nuevo.");
        }
        
        optUser = this.usuarioRepository.findByCorreo(usuario.getCorreo());
        if (optUser.isPresent()) {
            throw new BadRequestException("El correo del usuario ya se encuentra registrado. Intente de nuevo.");
        }
        
        Usuario userToSave = this.transformarUsuarioRqToUsuario(usuario);
        this.usuarioRepository.save(userToSave);
        
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("El usuario se ha creado satisfactoriamente.");
        return rta;
    }
    
    private Usuario transformarUsuarioRqToUsuario(UsuarioRq usuario) {
        Usuario user = new Usuario();
        user.setActivo(true);
        user.setCorreo(usuario.getCorreo());
        user.setFechaRegistro(LocalDateTime.now());
        user.setNombre(usuario.getNombre());
        user.setTelefono(usuario.getTelefono());
        return user;
    }

    @Override
    public UsuarioRs actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
    }

}

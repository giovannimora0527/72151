package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
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
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

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
    public RespuestaGenericaRs actualizarUsuario(Usuario usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findById(usuario.getIdUsuario());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario.");
        }

        Usuario userActual = optUser.get();
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente");
        if (!compararObjetosUsuarioActualizar(userActual, usuario)) {
            return rta;
        }

        if (!userActual.getNombre().equals(usuario.getNombre())) {
            // El nombre cambio
            if (this.usuarioRepository.existsByNombre(usuario.getNombre())) {
                throw new BadRequestException("El usuario " + usuario.getNombre() + ", existe en la bd. por favor verifique.");
            }
        }

        if (!userActual.getCorreo().equals(usuario.getCorreo())) {
            // El correo cambio
            if (this.usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new BadRequestException("El correo" + usuario.getCorreo() + ", existe en la bd. por favor verifique.");
            }
        }

        userActual.setNombre(usuario.getNombre());
        userActual.setCorreo(usuario.getCorreo());
        userActual.setTelefono(usuario.getTelefono());
        userActual.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActual);
        return rta;
    }

    private boolean compararObjetosUsuarioActualizar(Usuario usuarioActual, Usuario usuarioFront) {
        return !usuarioActual.getNombre().equals(usuarioFront.getNombre())
                || !usuarioActual.getCorreo().equals(usuarioFront.getCorreo())
                || !usuarioActual.getTelefono().equals(usuarioFront.getTelefono())
                || !usuarioActual.getActivo().equals(usuarioFront.getActivo());
    }
}

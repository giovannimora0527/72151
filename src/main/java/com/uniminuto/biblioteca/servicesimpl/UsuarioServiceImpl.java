package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import java.util.regex.Pattern;
import com.uniminuto.biblioteca.services.usuarioService;
=======
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
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579

/**
 *
 * @author lmora
 */
@Service
<<<<<<< HEAD
public class UsuarioServiceImpl implements usuarioService {

    @Autowired //Permite acceder  a la bd a traves de los metodos del repository, se podria hacer con un contructor
    private UsuarioRepository usuariosRepository;

    @Override
    public List<Usuario> obtenerListadoDeUsuarios() {return this.usuariosRepository.findAll();}
    //Obtiene todos los usuarios
    
    @Override
    public Usuario obtenerUsuariosPorId(Integer id_usuario) {
        Optional<Usuario> usuario = this.usuariosRepository.findById(id_usuario); // findBy(valor) retornar optional si existe .get(), si no lanza una exepción con runTimeEx otra alternativa es lanzar una excepción personalidad
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("ID del usuario no encontrado");
        }
    }

    @Override
    public Usuario obtenerUsuariosCorreo(String correo) { //Se valida el formato del correo con logic invers, si no es valida lanza una expeción y no avanza
        if (!correoValido(correo)) {throw new RuntimeException("El formato del correo no es valido"); }return usuariosRepository.findByCorreo(correo) //busca el usuarios con findByCorreo
                .orElseThrow(() -> new RuntimeException("El correo proporcionado no existe")); // si el usuarios no existe lanza una con or.Else indica otra expeción
}                //OrElseThrow hace parte del metodo de optional, si el correo no existe optional estara vacion y con orElseThrow lanza una exepción, pero si existe lo devuelve por el metodo
    //Metodo auxiliar para validar el formato del correo, ejemplo deben contener el @, tambien se puede agregar el (?i) dentro de " & ^ para que no sensitve a mys and minus
    private boolean correoValido(String correo) {
      String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
      return Pattern.matches(regex, correo);  } //Es del metodo pattern, se comprueba si una cadena cumple con un patron regular(regex), es la expresión regular defin el formt for cadenas
    // regex comprueba si cumple, lanzara un value boolean
}
=======
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
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579

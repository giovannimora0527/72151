package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByNombre(usuario.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el nombre "
                    + usuario.getNombre()
                    + ", Verifique e intente de nuevo.");
        }

        optUser = this.usuarioRepository
                .findByCorreo(usuario.getCorreo());

        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el correo "
                    + usuario.getCorreo()
                    + ", Verifique e intente de nuevo.");
        }
        this.usuarioRepository.save(this.convertirUsuarioRqToUsuario(usuario));
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("Se ha guardado el usuario con exito.");
        return rta;
    }

    private Usuario convertirUsuarioRqToUsuario(UsuarioRq usuario) {
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombre());
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setCorreo(usuario.getCorreo());
        user.setTelefono(usuario.getTelefono());
        return user;
    }

    @Override
    public UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException {        
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuario.getIdUsuario());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario.");
        }
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("El usuario se actualizó correctamente.");
        Usuario userActual = optUser.get();
        if (!cambioObjeto(userActual, usuario)) {
            return rta;
        }

        if (!usuario.getNombre().equals(userActual.getNombre())) {
           if (this.usuarioRepository.existsByNombre(usuario.getNombre())) {
               throw new BadRequestException("El nombre del usuario: " + usuario.getNombre() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        if (!usuario.getCorreo().equals(userActual.getCorreo())) {
            if (this.usuarioRepository.existsByCorreo(usuario.getCorreo())) {
               throw new BadRequestException("El correo del usuario: " + usuario.getCorreo() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        userActual.setNombre(usuario.getNombre());
        userActual.setCorreo(usuario.getCorreo());
        userActual.setTelefono(usuario.getTelefono());
        userActual.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActual);
        return rta;
    }

    private boolean cambioObjeto(Usuario userActual, Usuario userFront) {
        if (!userActual.getNombre().equals(userFront.getNombre())
                || !userActual.getCorreo().equals(userFront.getCorreo())
                || !userActual.getTelefono().equals(userFront.getTelefono())
                || !userActual.getActivo().equals(userFront.getActivo())) {
            return true;
        }
        return false;
    }
    
    @Transactional
    @Override
    public int procesarUsuariosCSV(String fileContent) throws BadRequestException {
        String[] lineas = fileContent.split("\\r\\n|\\r|\\n");
        int usuariosCargados = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String linea : lineas) {
            String[] campos = linea.split(",");
            if (campos.length == 5) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(campos[0].trim());
                nuevoUsuario.setCorreo(campos[1].trim());
                nuevoUsuario.setTelefono(campos[2].trim());
                try {
                     nuevoUsuario.setFechaRegistro(LocalDate.parse(campos[3].trim(), dateFormatter).atStartOfDay());
                } catch (Exception e) {
                    System.err.println("Error al parsear la fecha: " + campos[3].trim() + ". Se usará la fecha actual.");
                    nuevoUsuario.setFechaRegistro(LocalDate.now().atStartOfDay());
                }
                nuevoUsuario.setActivo(campos[4].trim().equalsIgnoreCase("true"));
                this.usuarioRepository.save(nuevoUsuario);
                usuariosCargados++;
            } else if (!linea.trim().isEmpty()) {
                System.err.println("Línea ignorada por formato incorrecto: " + linea);
            }
        }
        return usuariosCargados;
    }

}

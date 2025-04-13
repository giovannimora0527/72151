package com.uniminuto.biblioteca.apicontroller;

<<<<<<< HEAD
import com.uniminuto.biblioteca.entity.Usuario;
=======
import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.services.UsuarioService;
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.services.usuarioService;
/**
 *
 * @author ewilliams
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    /**
     * AutorService.
     */
    @Autowired
    private usuarioService UsuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
       return ResponseEntity.ok(this.UsuarioService.obtenerListadoDeUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> listarUsuariosId(@RequestParam Integer id_usuario) throws BadRequestException {
        return ResponseEntity.ok(this.UsuarioService.obtenerUsuariosPorId(id_usuario));
    }
    
    @Override
    public ResponseEntity<Usuario> listarUsuariosCorreo(@RequestParam String correo) throws BadRequestException {
        return ResponseEntity.ok(this.UsuarioService.obtenerUsuariosCorreo(correo));
    }
}
=======
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService.listarTodo());
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(String correo) throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService.buscarPorCorreo(correo));
    }

    @Override
    public ResponseEntity<UsuarioRs> guardarUsuario(UsuarioRq usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuario(usuario));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarUsuario(Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }
    
<<<<<<< HEAD
}
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
=======
}
>>>>>>> desarrollo

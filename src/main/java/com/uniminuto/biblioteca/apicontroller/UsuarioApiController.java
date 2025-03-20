package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ResponseEntity.ok(this.usuarioService.guardarUsuarioNuevo(usuario));
    }

    @Override
    public ResponseEntity<UsuarioRs> actualizarUsuario(Usuario usuario) throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }
    
    @PostMapping("/cargar-usuarios")
    public ResponseEntity<String> cargarUsuariosDesdeArchivo(@RequestBody String fileContent) {
        try {
            int usuariosCargados = usuarioService.procesarUsuariosCSV(fileContent);
            return ResponseEntity.ok("Se cargaron " + usuariosCargados + " usuarios exitosamente.");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo: " + e.getMessage());
        }
    }

}

package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** 
 *
 * @author 853345_MiguelRayo
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    /**
     * UsuarioService.
     */
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService.obtenerListadoUsuarios());
    }
    
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarioPorCorreo(String correo) 
            throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService
               .obtenerListadoUsuarioPorCorreo(correo));
    }
    
}

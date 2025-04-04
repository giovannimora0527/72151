package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
=======
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
/** 
 *
 * @author 853345_MiguelRayo
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    /**
     * UsuarioService.
=======
/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    
    /**
     * Servicio de usuarios.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
     */
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
<<<<<<< HEAD
       return ResponseEntity.ok(this.usuarioService.obtenerListadoUsuarios());
    }
    
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarioPorCorreo(String correo) 
            throws BadRequestException {
       return ResponseEntity.ok(this.usuarioService
               .obtenerListadoUsuarioPorCorreo(correo));
=======
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
    public ResponseEntity<UsuarioRs> actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
    }
    
}

package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
   /**
    * UsuarioService.
    */
   @Autowired
   private UsuarioService usuarioService;

   @Override
   public ResponseEntity<List<Usuario>> listarUsuario() throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.obtenerListadoUsuarios());
   }

   @Override
   public ResponseEntity<Optional<Usuario>> findUserByEmail(String correo) 
           throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.findUserByEmail(correo));
   }
}

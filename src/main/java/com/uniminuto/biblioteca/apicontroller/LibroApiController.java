package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author lmora
 */
@RestController
public class LibroApiController implements LibroApi {
    
    @Autowired
    private LibroService libroService;
    
    @Override
    public ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibros());
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }
    
    @Override
    public ResponseEntity<List<Libro>> obtenerLibroPorIdAutor(Integer autor) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroPorIdAutor(autor));
    }
    
    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(String titulo) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroPorNombre(titulo));
    }
    
}

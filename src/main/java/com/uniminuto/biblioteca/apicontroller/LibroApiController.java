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
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }
<<<<<<< HEAD
    
    @Override
    public ResponseEntity<List<Libro>> obtenerLibroPorIdAutor(Integer autor) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroPorIdAutor(autor));
    }
    
    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(String titulo) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroPorNombre(titulo));
    }
    
    @Override
    public ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(Integer anioPublicacionInicio, Integer anioPublicacionFin) throws BadRequestException {
        return ResponseEntity.ok(libroService.obtenerLibroPorFechaPublicacion(anioPublicacionInicio, anioPublicacionFin));
    }
    
}
=======

    @Override
    public ResponseEntity<List<Libro>>
            obtenerLibroPorAutor(Integer autorId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibrosPorAutor(autorId));
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(String nombreLibro)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroPorNombre(nombreLibro));
    }

    @Override
    public ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(
            Integer anioIni, Integer anioFin)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService
                .obtenerLibroXRangoPublicacion(anioIni, anioFin));
    }

}
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7

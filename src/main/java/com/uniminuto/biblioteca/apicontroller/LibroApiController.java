package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Libro;
<<<<<<< HEAD
import java.util.List;
=======
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.LibroService;
>>>>>>> desarrollo
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.api.LibroApi;
import com.uniminuto.biblioteca.services.libroService;
/**
 *
 * @author lmora
 */
@RestController  //Es un controlador REST e indica que todos los metodos retornara respuesta HTTP
public class LibroApiController implements LibroApi {
<<<<<<< HEAD
    /**
     * AutorService.
     */
    @Autowired //Inyecta instancia del libroService en el controlador
    private libroService LibroService;
=======

    @Autowired
    private LibroService libroService;
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579

    @Override
    public ResponseEntity<List<Libro>> listarLibros() throws BadRequestException {
       return ResponseEntity.ok(this.LibroService.obtenerListadoLibros());
    }

    @Override
<<<<<<< HEAD
    public ResponseEntity<Libro> listarLibrosId(@RequestParam Integer id_libro) throws BadRequestException {
        return ResponseEntity.ok(this.LibroService.obtenerLibrosPorId(id_libro));
    }
    
    @Override
    public ResponseEntity<List<Libro>> listarLibrosAutorId(@RequestParam Integer id_autor) throws BadRequestException {
        return ResponseEntity.ok(this.LibroService.obtenerLibrosAutorId(id_autor));
    }
    
    @Override
    public ResponseEntity<Libro> listarLibrosNombre(@RequestParam String titulo) throws BadRequestException {
        return ResponseEntity.ok(this.LibroService.obtenerLibrosNombre(titulo));
    }
    
    @Override //Indica que un metodo proviene de una clase padre, implementa metodo de LibroApi
    public ResponseEntity<List<Libro>> listarLibrosAnionRango(@RequestParam Integer inicioAnio,@RequestParam Integer finAnio) throws BadRequestException {
        return ResponseEntity.ok(this.LibroService.obtenerLibrosAnion(inicioAnio, finAnio)); //devuleve una respuesta con ok(200) llama al servicio para obtener los datos
    }
}
=======
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId)
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }

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

<<<<<<< HEAD
}
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
=======
    @Override
    public ResponseEntity<RespuestaGenericaRs> crearLibro(LibroRq LibroRq) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.crearLibro(LibroRq));
    }

}
>>>>>>> desarrollo

package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.LibroService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * API Controller para operaciones con libros.
 * 
 * @author lmora
 */
@RestController
@RequestMapping("/biblioteca/v1")
public class LibroApiController implements LibroApi {

    @Autowired
    private LibroService libroService;

    @Override
    @GetMapping("/listar-libros")
    public ResponseEntity<List<Libro>> listarLibros() throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibros());
    }

    @Override
    @GetMapping("/libro/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer id) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(id));
    }

    @Override
    @GetMapping("/libros-autor/{autorId}")
    public ResponseEntity<List<Libro>> obtenerLibroPorAutor(@PathVariable Integer autorId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibrosPorAutor(autorId));
    }

    @Override
    @GetMapping("/libro-nombre")
    public ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String nombreLibro) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroPorNombre(nombreLibro));
    }

    @Override
    @GetMapping("/libros-fecha-publicacion")
    public ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(@RequestParam Integer anioIni, @RequestParam Integer anioFin) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroXRangoPublicacion(anioIni, anioFin));
    }

    @Override
    @PostMapping("/crear-libro")
    public ResponseEntity<RespuestaGenericaRs> crearLibro(@RequestBody LibroRq libroRq) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.crearLibro(libroRq));
    }

    @Override
    @PutMapping("/actualizar-libro")
    public ResponseEntity<RespuestaGenericaRs> actualizarLibro(@RequestBody LibroRq libroRq) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.actualizarLibro(libroRq)); 
    }
}
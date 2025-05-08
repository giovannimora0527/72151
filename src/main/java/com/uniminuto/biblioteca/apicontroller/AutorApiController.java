package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author lmora
 */
@RestController
public class AutorApiController implements AutorApi {
    /**
     * AutorService.
     */
    @Autowired
    private AutorService autorService;
    
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerListadoAutores());
    }
    
    @Override
    public ResponseEntity<List<Nacionalidad>> listarNacionalidad() {
        List<Nacionalidad> lista = nacionalidadRepository.findAll(); // o nacionalidadService.listarTodas();
        return ResponseEntity.ok(lista);
    }
    @Override
    public ResponseEntity<List<Autor>> listarAutoresPorNacionalidad(Integer nacionalidadId) throws BadRequestException {
        List<Autor> autores = autorService.listarAutoresPorNacionalidad(nacionalidadId);
        return ResponseEntity.ok(autores);
    }
    @Override
    public ResponseEntity<Autor> listarAutorPorId(Integer autorId) throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerAutorPorId(autorId));
    }
    
    @Override
    public ResponseEntity<AutorRs> guardarAutor(AutorRq autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.guardarAutorNuevo(autor));
    }

    @Override
    public ResponseEntity<AutorRs> actualizarAutor(AutorRq autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.actualizarAutor(autor));
    }
   
}

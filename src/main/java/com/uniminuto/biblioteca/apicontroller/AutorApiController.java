package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que implementa la interfaz AutorApi para la gestión de autores.
 * Permite listar, crear y actualizar autores utilizando los servicios de AutorService.
 * 
 * @author lmora
 */
@RestController
public class AutorApiController implements AutorApi {

    /**
     * Servicio para la gestión de autores.
     */
    @Autowired
    private AutorService autorService;

    /**
     * Lista todos los autores registrados en la base de datos.
     *
     * @return ResponseEntity con la lista de autores.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerListadoAutores());
    }

    /**
     * Lista los autores filtrados por nacionalidad.
     *
     * @param nacionalidad Nacionalidad del autor a filtrar.
     * @return ResponseEntity con la lista de autores filtrados.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<List<Autor>> listarAutoresByNacionalidad(String nacionalidad) 
            throws BadRequestException {
       return ResponseEntity.ok(this.autorService
               .obtenerListadoAutoresPorNacionalidad(nacionalidad));
    }

    /**
     * Obtiene un autor por su identificador.
     *
     * @param autorId Identificador del autor.
     * @return ResponseEntity con el autor encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<Autor> listarAutorPorId(Integer autorId) throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerAutorPorId(autorId));
    }

    /**
     * Crea un nuevo autor en la base de datos.
     *
     * @param AutorRq Objeto de solicitud con los datos del autor a crear.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<RespuestaGenericaRs> crearAutor(AutorRq AutorRq) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.crearAutor(AutorRq));
    }

    /**
     * Actualiza la información de un autor existente.
     *
     * @param autor Objeto Autor con los datos actualizados.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarAutor(Autor autor) throws BadRequestException {
        return ResponseEntity.ok(this.autorService.actualizarAutor(autor));
    }
}

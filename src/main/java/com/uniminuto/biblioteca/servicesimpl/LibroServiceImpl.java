package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
<<<<<<< HEAD
import com.uniminuto.biblioteca.repository.AutorRepository;
=======
import com.uniminuto.biblioteca.services.AutorService;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import com.uniminuto.biblioteca.services.LibroService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    // Se inyecta el repositorio de Autor para validar la existencia del autor
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorService autorService;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId)
            throws BadRequestException {
        if (autorId == null) {
            throw new BadRequestException("El id del autor no puede ser vacio.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) {
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return !librosAutor.isEmpty() ? librosAutor : Collections.EMPTY_LIST;
    }

    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        if (nombreLibro.isBlank() || nombreLibro.isEmpty()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de "
                    + nombreLibro + ".");
        }
        return libro;
    }

    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(
            Integer fechaInicio, Integer fechaFin)
            throws BadRequestException {
        if (fechaInicio == null) {
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (fechaFin == null) {
            throw new BadRequestException("La fecha final es obligatoria.");
        }

        if (fechaFin < fechaInicio) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }

        return this.libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);
    }
    
    @Override
    public List<Libro> obtenerLibroPorIdAutor(Integer autor) throws BadRequestException {        
  
        // Validar que el autor esté registrado
        if (!autorRepository.existsById(autor)) {
            throw new BadRequestException("El autor con id " + autor + " no está registrado.");
        }
        // Retorna la lista de libros asociados al autor; si no hay, retorna lista vacía.
        return libroRepository.findByAutor_AutorId(autor);
    }
    
    @Override
    public Libro obtenerLibroPorNombre(String titulo) throws BadRequestException {
        if (titulo == null || titulo.isEmpty()) {
            throw new BadRequestException("El nombre del libro no puede estar vacío.");
        }
        
        // Eliminar espacios al inicio y final, y normalizar espacios internos.
        titulo = titulo.trim().replaceAll("\\s+", " ");
        
        // Se utiliza la consulta case sensitive para buscar coincidencias en el título.
        Libro libro = libroRepository.obtenerLibroPorNombre(titulo);
        if (libro == null) {
            throw new BadRequestException("No se encontró ningún libro con el nombre que contenga: " + titulo);
        }
        return libro;
    }
    
    @Override
    public List<Libro> obtenerLibroPorFechaPublicacion(Integer anioPublicacionInicio, Integer anioPublicacionFin) throws BadRequestException {
        // Validar que las fechas sean válidas
        if (anioPublicacionInicio == null || anioPublicacionFin == null) {
            throw new BadRequestException("El rango de fechas no puede ser nulo.");
        }
        if (anioPublicacionInicio > anioPublicacionFin) {
            throw new BadRequestException("La fecha de inicio no puede ser mayor a la fecha final.");
        }
        // Retorna la lista de libros cuyo anio_publicacion esté entre las fechas indicadas
        return libroRepository.findByAnioPublicacionBetween(anioPublicacionInicio, anioPublicacionFin);
    }
    
}

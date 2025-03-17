package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.LibroService;
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
}

package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface LibroService {
    List<Libro> listarLibros() throws BadRequestException;
    
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    List<Libro> obtenerLibroPorIdAutor(Integer autor) throws BadRequestException;
    
    // Método para buscar libro por nombre
    Libro obtenerLibroPorNombre(String titulo) throws BadRequestException;
    
    List<Libro> obtenerLibroPorFechaPublicacion(Integer anioPublicacionInicio, Integer anioPublicacionFin) throws BadRequestException;
    
}

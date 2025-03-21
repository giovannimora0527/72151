package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface LibroService {
    List<Libro> listarLibros() throws BadRequestException;

    Libro obtenerLibroId(Integer libroId) throws BadRequestException;

    List<Libro> obtenerLibroPorAutor(Integer autorId) throws BadRequestException;

    Optional<Libro> obtenerLibroPorTitulo(String name) throws BadRequestException;
    
    List<Libro> listarLibrosPorFecha(int anioInicio, int anioFin) throws BadRequestException;
}

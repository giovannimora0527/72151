package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author lmora
<<<<<<< HEAD
 */ //Se declaran metodos que deberán ser implementados por una clase concreta
public interface libroService {
    List<Libro> obtenerListadoLibros(); //Devuelv una list with all libre () cuando se indica un parametro dentro de () devuelv un valor especifico.
    
    Libro obtenerLibrosPorId(Integer id_libro);  
    
    List<Libro> obtenerLibrosAutorId(Integer id_autor);

    Libro obtenerLibrosNombre(String titulo); //devuelve un only value
    
    List<Libro> obtenerLibrosAnion(Integer inicioAnio, Integer finAnio);
=======
 */
public interface LibroService {
    /**
     * Lista todos los libros.
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    List<Libro> listarLibros() throws BadRequestException;
    
    /**
     * Obtiene un libro dado su id.
     * @param libroId Id del libro.
     * @return Libro.
     * @throws BadRequestException excepcion.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    /**
     * Obtiene los libros registrados dado un autor.
     * @param autorId Id del autor.
     * @return lista de libros.
     * @throws BadRequestException excepcion.
     */
    List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException;
    
    
    /**
     * Obtiene un libro dado su nombre.
     * @param nombreLibro Nombre del libro.
     * @return Libro.
     * @throws BadRequestException excepcion.
     */
    Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException;
    
    /**
     * Obtiene el listado de libros dado la fecha de publicacion.
     * @param anioIni Año inicial de la consulta.
     * @param anioFin Año final de la consulta.
     * @return Lista de libros que cumplen con el criterio.
     * @throws BadRequestException excepcion.
     */
    List<Libro> obtenerLibroXRangoPublicacion(Integer anioIni, 
            Integer anioFin) throws BadRequestException;
<<<<<<< HEAD
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
}
=======
    
    /**
     * 
     * @param LibroRq
     * @return
     * @throws BadRequestException 
     */
    RespuestaGenericaRs crearLibro(LibroRq LibroRq) throws BadRequestException;
}
>>>>>>> desarrollo

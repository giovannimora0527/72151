package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz para los servicios de Libro.
 * 
 * @author lmora
 */
public interface LibroService {
    /**
     * Lista todos los libros.
     * @return Lista de libros registrados.
     * @throws BadRequestException excepción.
     */
    List<Libro> listarLibros() throws BadRequestException;

    /**
     * Obtiene un libro dado su id.
     * @param libroId Id del libro.
     * @return Libro.
     * @throws BadRequestException excepción.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    /**
     * Método abreviado para obtener un libro por id.
     * @param libroId Id del libro.
     * @return Libro.
     * @throws BadRequestException excepción.
     */
    Libro obtener(Integer libroId) throws BadRequestException;

    /**
     * Obtiene los libros registrados dado un autor.
     * @param autorId Id del autor.
     * @return Lista de libros.
     * @throws BadRequestException excepción.
     */
    List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException;

    /**
     * Obtiene un libro dado su nombre.
     * @param nombreLibro Nombre del libro.
     * @return Libro.
     * @throws BadRequestException excepción.
     */
    Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException;

    /**
     * Obtiene el listado de libros dado la fecha de publicación.
     * @param anioIni Año inicial de la consulta.
     * @param anioFin Año final de la consulta.
     * @return Lista de libros que cumplen con el criterio.
     * @throws BadRequestException excepción.
     */
    List<Libro> obtenerLibroXRangoPublicacion(Integer anioIni, Integer anioFin) throws BadRequestException;

    /**
     * Crea un nuevo libro.
     * @param libroRq Datos del libro.
     * @return Respuesta con el resultado de la operación.
     * @throws BadRequestException excepción.
     */
    RespuestaGenericaRs crearLibro(LibroRq libroRq) throws BadRequestException;

    /**
     * Verifica si el libro está disponible.
     * @param idLibro Id del libro.
     * @return True si está disponible, false en caso contrario.
     * @throws BadRequestException excepción.
     */
    boolean estaDisponible(Integer idLibro) throws BadRequestException;

    /**
     * Actualiza un libro existente.
     * @param libroRq Datos del libro a actualizar.
     * @return Respuesta con el resultado de la operación.
     * @throws BadRequestException excepción.
     */
    RespuestaGenericaRs actualizarLibro(LibroRq libroRq) throws BadRequestException;
}
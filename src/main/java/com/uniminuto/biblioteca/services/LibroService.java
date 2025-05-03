package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Servicio que define las operaciones para la gestión de libros.
 * Proporciona métodos para listar, consultar, crear y actualizar libros.
 * 
 * @author lmora
 */
public interface LibroService {
    /**
     * Lista todos los libros registrados en la base de datos.
     * 
     * @return Lista de libros registrados.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    List<Libro> listarLibros() throws BadRequestException;
    
    /**
     * Obtiene un libro dado su id.
     * 
     * @param libroId Id del libro.
     * @return Libro encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    /**
     * Obtiene los libros registrados dado un autor.
     * 
     * @param autorId Id del autor.
     * @return Lista de libros asociados al autor.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException;
    
    /**
     * Obtiene un libro dado su nombre.
     * 
     * @param nombreLibro Nombre del libro.
     * @return Libro encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException;
    
    /**
     * Obtiene el listado de libros dado un rango de años de publicación.
     * 
     * @param anioIni Año inicial de la consulta.
     * @param anioFin Año final de la consulta.
     * @return Lista de libros que cumplen con el criterio.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    List<Libro> obtenerLibroXRangoPublicacion(Integer anioIni, 
            Integer anioFin) throws BadRequestException;
    
    /**
     * Crea un nuevo libro en la base de datos.
     * 
     * @param LibroRq Objeto de solicitud con los datos del libro a crear.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    RespuestaGenericaRs crearLibro(LibroRq LibroRq) throws BadRequestException;

    /**
     * Lista los libros con existencias disponibles (existencias >= 1).
     * 
     * @return Lista de libros disponibles.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    List<Libro> listarLibrosDisponibles() throws BadRequestException;

    /**
     * Actualiza las existencias de un libro.
     * 
     * @param libroId Id del libro.
     * @param nuevasExistencias Nuevo valor de existencias.
     * @return Respuesta del servicio.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    RespuestaGenericaRs actualizarExistencias(Integer libroId, Integer nuevasExistencias) throws BadRequestException;
}

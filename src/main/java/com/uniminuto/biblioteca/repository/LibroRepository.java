package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    /**
     * Obtiene la lista dado un autor.
     * @param autor Autor a buscar.
     * @return Lista de libros.
     */
    List<Libro> findByAutor(Autor autor);
    
    /**
     * Busca un libro por su nombre.
     * @param nombreLibro Nombre del libro a buscar.
     * @return Libro.
     */
    Libro findByTitulo(String nombreLibro);
    
    /**
     * Lista los libros por rango de fecha de publicacion.
     * @param anioIni Año inicial.
     * @param anioFin Año final.
     * @return Lista de libros que cumplen el criterio.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioIni, Integer anioFin);
    
    /**
     * Consulta el libro por titulo.
     */
    boolean existsByTitulo(String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.existencias > 1 OR " +
       "l.idLibro NOT IN (SELECT p.libro.idLibro FROM Prestamo p WHERE p.estado = 'PRESTADO')")
    List<Libro> findLibrosDisponibles();
    
}
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    List<Libro> findByAutor_AutorId(Integer autor);
    
    // Método para buscar un libro por nombre (coincidencia de subcadena, case sensitive)
    @Query(value = "SELECT * FROM libros WHERE BINARY titulo LIKE CONCAT('%', :titulo, '%')", nativeQuery = true)
    Libro obtenerLibroPorNombre(@Param("titulo") String titulo);
}

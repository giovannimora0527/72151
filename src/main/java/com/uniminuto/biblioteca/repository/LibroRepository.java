package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
                JpaRepository<Libro, Integer> {

        List<Libro> findByAutor_AutorId(Integer autorId);

        Optional<Libro> findByTitulo(String nombre);
        

        //Consulta para filtrar libros por rango de años(int)
        @Query("SELECT l FROM Libro l WHERE l.anioPublicacion BETWEEN :anioInicio AND :anioFin")
        List<Libro> findByAnioPublicacionBetween(@Param("anioInicio") int anioInicio, @Param("anioFin") int anioFin);

        
}

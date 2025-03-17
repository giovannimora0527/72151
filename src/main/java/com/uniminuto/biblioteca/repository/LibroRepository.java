package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    List<Libro> findByAutor_AutorId(Integer autor);
}

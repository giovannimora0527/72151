package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer>  {
    
}

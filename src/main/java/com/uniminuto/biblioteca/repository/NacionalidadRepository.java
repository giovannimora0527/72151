package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface NacionalidadRepository extends
        JpaRepository<Nacionalidad, Integer> {
}
  

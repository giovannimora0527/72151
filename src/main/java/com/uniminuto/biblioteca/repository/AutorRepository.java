package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer>  {


    Boolean existsByNombre(String nombre);

    Boolean existsByNacionalidad(String nacionalidad);

    Optional<Autor> findByNacionalidad(String nacionalidad);

    Optional<Autor> findByFechaNacimiento(LocalDate fechaNacimiento);
    
    List<Autor> findAllByOrderByFechaNacimientoAsc();
    
    List<Autor> findAllByOrderByFechaNacimientoDesc();
}

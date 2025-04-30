package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer>  {

    Boolean existsByNombre(String nombre);
Optional<Autor> findByNombre(String nombre);

    Boolean existsByNacionalidad(Nacionalidad nacionalidad);

@Query("SELECT a FROM Autor a JOIN FETCH a.nacionalidad WHERE a.autorId = :id")
Optional<Autor> findByIdWithNacionalidad(@Param("id") Integer id);

    Optional<Autor> findByFechaNacimiento(LocalDate fechaNacimiento);

    List<Autor> findAllByOrderByFechaNacimientoAsc();

    List<Autor> findAllByOrderByFechaNacimientoDesc();
}
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uniminuto.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer>  {


    Boolean existsByNombre(String nombre);

    Boolean existsByNacionalidad(String nacionalidad);

    List<Autor> findByNacionalidad(String nacionalidad);

    Optional<Autor> findByFechaNacimiento(LocalDate fechaNacimiento);

    List<Autor> findAllByOrderByFechaNacimientoAsc();

    Optional<Autor> findByNombre(String nombre);
}
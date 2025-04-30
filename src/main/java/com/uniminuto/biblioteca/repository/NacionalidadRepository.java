package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NacionalidadRepository extends JpaRepository<Nacionalidad, Integer> {
    boolean existsByNombre(String nombre);
    Nacionalidad findByNombre(String nombre);
}

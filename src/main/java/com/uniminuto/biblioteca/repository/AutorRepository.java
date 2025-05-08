package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer>  {
    
    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial,
            LocalDate fechaNacimientoFin);
    
    List<Autor> findAllByOrderByFechaNacimientoAsc();
    
    
    @Query("SELECT DISTINCT a.nacionalidad FROM Autor a")
    List<Nacionalidad> listarNacionalidadesDeAutores();
    
    
    List<Autor> findByNacionalidad_NacionalidadId(Integer nacionalidadId);
    
    
    List<Autor> findAllByOrderByFechaNacimientoDesc();
    
    /**
     * Busca un autor por nombre.
     *
     * @param nombre nombre del autor.
     * @return autor que cumpla con el criterio.
     */
    Optional<Autor> findByNombre(String nombre);

    /**
     * Verifica si existe un autor por nombre.
     *
     * @param nombre nombre del autor.
     * @return true si existe.
     */
    boolean existsByNombre(String nombre);
    

}

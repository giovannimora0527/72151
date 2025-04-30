package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lmora
 */
@Repository
public interface PrestamoRepository  extends
                JpaRepository<Prestamo, Integer> {

    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.libro.idLibro = :idLibro AND p.fechaEntrega IS NULL")
    int countPrestamosActivosPorLibro(@Param("idLibro") Integer idLibro);
}

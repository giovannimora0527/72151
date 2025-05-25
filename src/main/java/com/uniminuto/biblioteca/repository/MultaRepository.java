package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Multa;
import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MiguelRayo_853345
 **/
@Repository
public interface MultaRepository extends JpaRepository<Multa, Integer>  {
    
    // Aquí podríamos añadir consultas personalizadas,
    // por ejemplo: List<Multa> findByEstado(EstadoMulta estado);
    
    List<Multa> findAllByOrderByFechaMultaDesc();
    
    Optional<Multa> findByPrestamo(Prestamo prestamo);
    
}
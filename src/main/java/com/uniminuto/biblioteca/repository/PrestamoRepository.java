package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MiguelRayo_853345
 **/
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>  {
    
    // Aquí podríamos añadir consultas personalizadas,
    // por ejemplo: List<Prestamo> findByEstado(EstadoPrestamo estado);
    
    List<Prestamo> findAllByOrderByFechaPrestamoDesc();
    
}
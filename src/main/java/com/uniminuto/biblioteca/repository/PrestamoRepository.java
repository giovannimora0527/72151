
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author santiago
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    // Cambiar por estado != DEVUELTO
    Optional<Prestamo> findByUsuarioAndLibroAndEstadoNot(Usuario usuario, Libro libro, EstadoPrestamo estado);
List<Prestamo> findAllByOrderByFechaPrestamoDesc();
 Optional<Prestamo> findByFechaEntrega(LocalDateTime fechaEntrega); 
}
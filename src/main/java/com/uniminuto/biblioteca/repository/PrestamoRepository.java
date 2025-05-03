package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Repositorio para la entidad Prestamo.
 * Proporciona métodos CRUD y consultas personalizadas para la gestión de préstamos.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    // Ejemplo de método personalizado (comentado):
    // /**
    //  * Valida si un préstamo es válido según la fecha actual y la fecha de devolución.
    //  *
    //  * @param FechaActual Fecha y hora actual.
    //  * @param FechaDevolucion Fecha y hora de devolución del préstamo.
    //  * @return true si el préstamo es válido, false en caso contrario.
    //  */
    // boolean prestamValid (LocalDateTime FechaActual,  LocalDateTime FechaDevolucion);
}

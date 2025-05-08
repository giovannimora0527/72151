package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceso a datos de préstamos
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    
    /**
     * Verifica si existe un préstamo activo para un libro específico
     * @param libro El libro a verificar
     * @param estado El estado del préstamo a buscar
     * @return true si existe, false en caso contrario
     */
    boolean existsByLibroAndEstado(Libro libro, Prestamo.EstadoPrestamo estado);
    
    /**
     * Cuenta cuántos préstamos activos hay para un libro específico
     * @param libro El libro a verificar
     * @param estados Lista de estados a considerar
     * @return Número de préstamos activos
     */
    long countByLibroAndEstadoIn(Libro libro, List<Prestamo.EstadoPrestamo> estados);
}
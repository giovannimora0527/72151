package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
//    boolean prestamValid (LocalDateTime FechaActual,  LocalDateTime FechaDevolucion);
}

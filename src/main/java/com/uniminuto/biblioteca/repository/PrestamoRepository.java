package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author lmora
 */
@Repository
public interface PrestamoRepository  extends
                JpaRepository<Prestamo, Integer> {
}

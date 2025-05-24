package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Deuda.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 *
 * @author santiago
 */
@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {

    /**
     * Buscar deuda por préstamo.
     * @param prestamo
     * @return 
     */
    Optional<Deuda> findByPrestamo(Prestamo prestamo);

    /**
     * Listar todas las deudas ordenadas por ID descendente.
     * @return 
     */
    List<Deuda> findAllByOrderByIdDeudaDesc();

    /**
     * Buscar deudas por estado de pago.
     * @param estadoPago
     * @return 
     */
    List<Deuda> findByEstadoPago(EstadoPago estadoPago);
}


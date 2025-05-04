package com.uniminuto.biblioteca.services;


import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.time.LocalDate;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PrestamoService {
    /**
     * Lista todos los libros.
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    List<Prestamo> listarPrestamos() throws BadRequestException;

    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;
    RespuestaGenericaRs actualizarPrestamo(Integer idPrestamo, LocalDate fechaEntrega) throws BadRequestException;
}
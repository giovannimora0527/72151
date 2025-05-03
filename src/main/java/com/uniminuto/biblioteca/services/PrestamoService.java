package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;
/**
 *
 * @author santiago
 */
public interface PrestamoService {
    List<Prestamo> obtenerListadoPrestamos();

    Prestamo obtenerPrestamoPorId(Integer id) throws BadRequestException;

    PrestamoRs guardarPrestamo(PrestamoRq prestamo) throws BadRequestException;

    RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;
}



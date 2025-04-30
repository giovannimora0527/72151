package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PrestamoService  {
    List<PrestamoDto> listarPrestamos();
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;
}

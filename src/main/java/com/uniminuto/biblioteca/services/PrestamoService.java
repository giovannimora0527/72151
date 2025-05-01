package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PrestamoService {

    List<Prestamo> listarPrestamos() throws BadRequestException;
    Prestamo obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException;
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;
    RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;
}

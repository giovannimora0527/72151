package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.PrestamoRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author MiguelRayo_853345
 **/
public interface PrestamoService {
    
    List<Prestamo> listarPrestamos() throws BadRequestException;
    
    Prestamo listarPrestamoPorId(Integer prestamoId) throws BadRequestException;
    
    RespuestaGenericaRs guardarPrestamo(PrestamoRq prestamo) throws BadRequestException;
    
    RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;
}
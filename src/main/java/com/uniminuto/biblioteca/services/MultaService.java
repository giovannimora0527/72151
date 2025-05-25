package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Multa;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.MultaRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author MiguelRayo_853345
 **/
public interface MultaService {
    
    List<Multa> listarMultas() throws BadRequestException;
    
    Multa listarMultaPorId(Integer multaId) throws BadRequestException;
    
    void guardarMulta(Prestamo prestamo);
    
    RespuestaGenericaRs actualizarMulta(Multa multa) throws BadRequestException;
}
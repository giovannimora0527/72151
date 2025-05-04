package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.AutorRq;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();
    
    List<Autor> obtenerListadoAutoresPorNacionalidad(Integer nacionalidad) throws BadRequestException;
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
    
    RespuestaGenericaRs guardarAutor(AutorRq autor) throws BadRequestException;
    
    RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException;
}
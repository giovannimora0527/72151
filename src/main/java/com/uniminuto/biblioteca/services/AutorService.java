package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();

    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;

    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;

    RespuestaGenericaRs crearAutor(AutorRq autorRq) throws BadRequestException;

    RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException;
}
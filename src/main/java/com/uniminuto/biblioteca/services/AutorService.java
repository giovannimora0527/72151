package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.*;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();

    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;

    AutorRs guardarAutor(AutorRq autor) throws BadRequestException;

    RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException;
}

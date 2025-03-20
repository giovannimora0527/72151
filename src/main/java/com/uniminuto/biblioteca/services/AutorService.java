package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();
    
    Autor obtenerAutoresPorId(Integer autorId);
}

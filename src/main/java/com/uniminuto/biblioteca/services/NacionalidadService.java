package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;

/**
 *
 * @author lmora
 */
public interface NacionalidadService {

    // Método para obtener la lista de nacionalidades
    List<Nacionalidad> obtenerListaNacionalidades();
}

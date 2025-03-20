package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;

/**
 *
 * @author lmora
 */ //Se declaran metodos que deberán ser implementados por una clase concreta
public interface libroService {
    List<Libro> obtenerListadoLibros(); //Devuelv una list with all libre () cuando se indica un parametro dentro de () devuelv un valor especifico.
    
    Libro obtenerLibrosPorId(Integer id_libro);  
    
    List<Libro> obtenerLibrosAutorId(Integer id_autor);

    Libro obtenerLibrosNombre(String titulo); //devuelve un only value
    
    List<Libro> obtenerLibrosAnion(Integer inicioAnio, Integer finAnio);
}

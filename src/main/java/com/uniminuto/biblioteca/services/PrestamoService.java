package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;

import java.util.List;

/**
 * Interfaz que define los servicios relacionados con la gestión de préstamos
 */
public interface PrestamoService {
    
    /**
     * Obtiene todos los préstamos registrados en el sistema
     * @return Lista de objetos PrestamoRs con la información de los préstamos
     */
    List<PrestamoRs> getPrestamos();
    
    /**
     * Registra un nuevo préstamo en el sistema
     * @param rq Objeto con la información del préstamo a crear
     * @return Objeto PrestamoRs con la información del préstamo creado
     */
    PrestamoRs guardarPrestamo(PrestamoRq rq);
    
    /**
     * Actualiza un préstamo existente, principalmente la fecha de entrega
     * @param id ID del préstamo a actualizar
     * @param rq Objeto con la información del préstamo a actualizar
     * @return Objeto PrestamoRs con la información del préstamo actualizado
     */
    PrestamoRs actualizarPrestamo(Long id, PrestamoRq rq);
    
    /**
     * Obtiene la lista de libros disponibles para préstamo
     * Un libro está disponible si tiene más existencias que préstamos activos
     * @return Lista de libros disponibles
     */
    List<Libro> getLibrosDisponibles();
}
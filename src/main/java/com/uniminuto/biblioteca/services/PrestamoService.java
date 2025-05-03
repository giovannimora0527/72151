package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamosActualizarRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;

import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PrestamoService  {
   /**
     * Lista todos los préstamos registrados en el sistema.
     * @return Lista de objetos PrestamoDto con la información de cada préstamo.
     */
    List<PrestamoDto> listarPrestamos();

    /**
     * Crea un nuevo préstamo a partir de la información proporcionada.
     * @param prestamoRq Objeto que contiene los datos necesarios para registrar un préstamo.
     * @return Respuesta genérica con el resultado del proceso.
     * @throws BadRequestException En caso de datos inválidos o errores de negocio.
     */
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza un préstamo existente, por ejemplo, para registrar la devolución de un libro.
     * @param prestamosActualizarRq Objeto con los datos necesarios para actualizar el préstamo.
     * @return Respuesta genérica con el resultado del proceso.
     * @throws BadRequestException En caso de errores en los datos recibidos o reglas de negocio.
     */
    RespuestaGenericaRs actualizarPrestamo(PrestamosActualizarRq prestamosActualizarRq) throws BadRequestException;
}
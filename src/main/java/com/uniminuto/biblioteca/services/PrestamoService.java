package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

/**
 * Servicio que define las operaciones para la gestión de préstamos.
 */
public interface PrestamoService {

    /**
     * Lista todos los préstamos registrados en la base de datos.
     *
     * @return Lista de préstamos.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    List<Prestamo> listarPrestamos() throws BadRequestException;

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param prestamoId Identificador del préstamo.
     * @return El préstamo encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    Prestamo obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException;

    /**
     * Crea un nuevo préstamo en la base de datos.
     *
     * @param prestamoRq Objeto de solicitud con los datos del préstamo a crear.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza la información de un préstamo existente.
     *
     * @param prestamo Objeto Prestamo con los datos actualizados.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;
}

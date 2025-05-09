package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
// import java.time.LocalDate; // <-- Ya no se usa aquí para actualizarPrestamo
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz del servicio para la gestión de préstamos.
 * Define las operaciones disponibles para los préstamos.
 * @author lmora
 */
public interface PrestamoService {
    /**
     * Lista todos los préstamos.
     * @return Lista de préstamos registrados.
     * @throws BadRequestException excepcion.
     */
    List<Prestamo> listarPrestamos() throws BadRequestException;

    /**
     * Crea un nuevo préstamo.
     * @param prestamoRq Datos del préstamo a crear.
     * @return Respuesta genérica.
     * @throws BadRequestException excepcion.
     */
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza un préstamo existente (usado para registrar devolución).
     * @param idPrestamo ID del préstamo a actualizar.
     * @param fechaEntregaDesdeFrontend String con la fecha y hora de entrega desde el frontend ("YYYY-MM-DDTHH:mm").
     * @return Respuesta genérica.
     * @throws BadRequestException excepcion.
     */
    // **AJUSTE:** Cambiar la firma para que reciba un String, coincidiendo con la implementación
    public RespuestaGenericaRs actualizarPrestamo(Integer idPrestamo, String fechaEntregaDesdeFrontend) throws BadRequestException;
    public RespuestaGenericaRs archivarPrestamo(Integer idPrestamo) throws BadRequestException;
}
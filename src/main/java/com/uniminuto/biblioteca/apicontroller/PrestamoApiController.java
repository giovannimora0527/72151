package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que implementa la interfaz PrestamoApi para la gestión de préstamos.
 * Permite listar, crear y actualizar préstamos utilizando los servicios de PrestamoService.
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    /**
     * Servicio para la gestión de préstamos.
     */
    @Autowired
    private PrestamoService prestamoService;

    /**
     * Lista todos los préstamos registrados en la base de datos.
     *
     * @return ResponseEntity con la lista de préstamos.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param prestamoId Identificador del préstamo.
     * @return ResponseEntity con el préstamo encontrado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<Prestamo> obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamoPorId(prestamoId));
    }

    /**
     * Crea un nuevo préstamo en la base de datos.
     *
     * @param prestamoRq Objeto de solicitud con los datos del préstamo a crear.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<RespuestaGenericaRs> crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.crearPrestamo(prestamoRq));
    }

    /**
     * Actualiza la información de un préstamo existente.
     *
     * @param prestamo Objeto Prestamo con los datos actualizados.
     * @return ResponseEntity con la respuesta genérica del resultado.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.actualizarPrestamo(prestamo));
    }
}

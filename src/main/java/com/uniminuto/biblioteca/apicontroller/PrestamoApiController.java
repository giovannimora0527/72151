package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar préstamos de la biblioteca
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Lista todos los préstamos registrados
     * @return Lista de préstamos
     * @throws BadRequestException si ocurre un error
     */
    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamos() throws BadRequestException {
        try {
            List<PrestamoRs> prestamos = prestamoService.getPrestamos();
            return new ResponseEntity<>(prestamos, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Crea un nuevo préstamo
     * @param prestamoRq Datos del préstamo a crear
     * @return Información del préstamo creado
     * @throws BadRequestException si ocurre un error
     */
    @Override
    public ResponseEntity<PrestamoRs> crearPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException {
        try {
            PrestamoRs prestamo = prestamoService.guardarPrestamo(prestamoRq);
            return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Actualiza un préstamo existente
     * @param id ID del préstamo a actualizar
     * @param prestamoRq Datos del préstamo a actualizar
     * @return Información del préstamo actualizado
     * @throws BadRequestException si ocurre un error
     */
    @Override
    public ResponseEntity<PrestamoRs> actualizarPrestamo(
            @PathVariable("id") Long id,
            @RequestBody PrestamoRq prestamoRq) throws BadRequestException {
        try {
            PrestamoRs prestamo = prestamoService.actualizarPrestamo(id, prestamoRq);
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Obtiene la lista de libros disponibles para préstamo
     * @return Lista de libros disponibles
     * @throws BadRequestException si ocurre un error
     */
    @Override
    public ResponseEntity<List<Libro>> obtenerLibrosDisponibles() throws BadRequestException {
        try {
            List<Libro> librosDisponibles = prestamoService.getLibrosDisponibles();
            return new ResponseEntity<>(librosDisponibles, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Genera un mensaje informativo
     * @param mensaje El mensaje a incluir en la respuesta
     * @return Respuesta con el mensaje
     */
    private ResponseEntity<Map<String, String>> crearMensaje(String mensaje) {
        return new ResponseEntity<>(Map.of("message", mensaje), HttpStatus.OK);
    }
}
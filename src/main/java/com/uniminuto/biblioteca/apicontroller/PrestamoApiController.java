package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author lmora
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamo()
            throws BadRequestException {
        return ResponseEntity.ok(this.prestamoService.listarPrestamos());
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        return ResponseEntity.ok(this.prestamoService.crearPrestamo(prestamoRq));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo) // La firma coincide con la interfaz API
            throws BadRequestException {

        // ** VALIDACION: Asegurarse de que el Prestamo recibido tenga al menos el ID y la fecha de entrega **
        if (prestamo == null || prestamo.getIdPrestamo() == null || prestamo.getFechaEntrega() == null) {
             throw new BadRequestException("Datos incompletos para actualizar el prestamo. Se requiere ID y fecha de entrega.");
        }

        // ** EXTRAER el ID del prestamo y la fecha de entrega del objeto Prestamo recibido **
        Integer idPrestamo = prestamo.getIdPrestamo();
        LocalDate fechaEntrega = prestamo.getFechaEntrega(); // Asumiendo que fechaEntrega es LocalDate en la entidad Prestamo

        // ** LLAMAR al servicio con los parametros que espera (Integer, LocalDate) **
        return ResponseEntity.ok(this.prestamoService.actualizarPrestamo(idPrestamo, fechaEntrega));
    }
}
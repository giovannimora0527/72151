package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PrestamoService;
// import java.time.LocalDate; // <-- Ya no se usa para parsear o pasar al servicio
// import java.time.LocalDateTime; // Puedes importar si lo necesitas aquí, pero el parseo se hace en el servicio
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador API para la gestión de préstamos.
 * Implementa la interfaz PrestamoApi.
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
    public ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(
            @PathVariable("id") Integer idPrestamo,
            @RequestBody Map<String, String> fechaEntregaBody // Recibe el Map con el string
        ) throws BadRequestException {

        if (fechaEntregaBody == null || !fechaEntregaBody.containsKey("fechaEntrega") || fechaEntregaBody.get("fechaEntrega") == null || fechaEntregaBody.get("fechaEntrega").isEmpty()) {
             throw new BadRequestException("Se requiere la fecha de entrega en el cuerpo de la solicitud.");
        }

        String fechaEntregaString = fechaEntregaBody.get("fechaEntrega"); // **AJUSTE:** Obtiene el string

        // **AJUSTE:** Llama al servicio pasando el String directamente (el parseo a LocalDateTime se hace DENTRO del servicio)
        return ResponseEntity.ok(this.prestamoService.actualizarPrestamo(idPrestamo, fechaEntregaString));
    }
    
    @Override // **AJUSTE:** Anotación @Override
    // **AJUSTE:** Cambiado el nombre del método de cancelarPrestamo a archivarPrestamo
    public ResponseEntity<RespuestaGenericaRs> archivarPrestamo(@PathVariable("id") Integer idPrestamo)
         throws BadRequestException {
        // Llama al servicio para archivar el préstamo
        return ResponseEntity.ok(this.prestamoService.archivarPrestamo(idPrestamo));
    }
}
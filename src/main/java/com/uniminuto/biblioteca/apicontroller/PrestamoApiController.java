
package com.uniminuto.biblioteca.apicontroller;
import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.coyote.BadRequestException;
/**
 *
 * @author santiago
 */

@RestController
@RequestMapping("/prestamo")
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        List<Prestamo> prestamos = prestamoService.obtenerListadoPrestamos();
        return ResponseEntity.ok(prestamos);
    }

    @Override
    public ResponseEntity<Prestamo> listarPrestamoPorId(@RequestParam Integer prestamoId) throws BadRequestException {
        Prestamo prestamo = prestamoService.obtenerPrestamoPorId(prestamoId);
        return ResponseEntity.ok(prestamo);
    }

    @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException {
        PrestamoRs respuesta = prestamoService.guardarPrestamo(prestamoRq);
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarPrestamo(@RequestBody Prestamo prestamo) throws BadRequestException {
        RespuestaGenericaRs respuesta = prestamoService.actualizarPrestamo(prestamo);
        return ResponseEntity.ok(respuesta);
    }
}


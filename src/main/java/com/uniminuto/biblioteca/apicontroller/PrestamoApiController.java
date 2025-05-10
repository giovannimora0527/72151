package com.uniminuto.biblioteca.controller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> guardarPrestamo(Prestamo prestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.guardarPrestamo(prestamo));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> entregarPrestamo(Prestamo prestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.entregarPrestamo(prestamo));
    }
}

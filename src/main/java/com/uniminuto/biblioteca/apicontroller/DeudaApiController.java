package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.DeudaApi;
import com.uniminuto.biblioteca.model.DeudaRq;
import com.uniminuto.biblioteca.model.DeudaRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.model.ActualizarDeudaRq;
import com.uniminuto.biblioteca.services.DeudaService;

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
@RequestMapping("/deuda")
public class DeudaApiController implements DeudaApi {

    @Autowired
    private DeudaService deudaService;

    @Override
    public ResponseEntity<List<Deuda>> listarDeudas() throws BadRequestException {
        List<Deuda> deudas = deudaService.obtenerListadoDeudas();
        return ResponseEntity.ok(deudas);
    }

    @Override
    public ResponseEntity<Deuda> obtenerDeudaPorId(Integer deudaId) throws BadRequestException {
        Deuda deuda = deudaService.obtenerDeudaPorId(deudaId);
        return ResponseEntity.ok(deuda);
    }

    @Override
    public ResponseEntity<DeudaRs> guardarDeuda(@RequestBody DeudaRq deudaRq) throws BadRequestException {
        DeudaRs respuesta = deudaService.guardarDeuda(deudaRq);
        return ResponseEntity.ok(respuesta);
    }

    @Override
public ResponseEntity<RespuestaGenericaRs> actualizarDeuda(@RequestBody ActualizarDeudaRq rq) throws BadRequestException {
    RespuestaGenericaRs respuesta = deudaService.actualizarDeuda(rq);
    return ResponseEntity.ok(respuesta);
}
}


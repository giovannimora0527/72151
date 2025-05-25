package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.MultaApi;
import com.uniminuto.biblioteca.entity.Multa;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.MultaRq;
import com.uniminuto.biblioteca.services.MultaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MiguelRayo_853345
 **/
@RestController
public class MultaApiController implements MultaApi {
    /**
     * AutorService.
     */
    @Autowired
    private MultaService multaService;

    @Override
    public ResponseEntity<List<Multa>> listarMultas() throws BadRequestException {
       return ResponseEntity.ok(this.multaService.listarMultas());
    }

    @Override
    public ResponseEntity<Multa> listarMultaPorId(Integer multaId) throws BadRequestException {
       return ResponseEntity.ok(this.multaService.listarMultaPorId(multaId));
    }

    @Override
    public ResponseEntity<RespuestaGenericaRs> actualizarMulta(Multa multa) throws BadRequestException {
        return ResponseEntity.ok(this.multaService.actualizarMulta(multa));
    }
}
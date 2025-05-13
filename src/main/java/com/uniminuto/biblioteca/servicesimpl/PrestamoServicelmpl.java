package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;

import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicelmpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    public RespuestaGenericaRs guardarPrestamo(Prestamo prestamo) {
        prestamo.setEstado("PRESTADO");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamoRepository.save(prestamo);

        RespuestaGenericaRs respuesta = new RespuestaGenericaRs();
        respuesta.setMessage("Préstamo registrado correctamente.");
        return respuesta;
    }

    @Override
    public RespuestaGenericaRs entregarPrestamo(Prestamo prestamo) {
        Prestamo existente = prestamoRepository.findById(prestamo.getId()).orElse(null);

        RespuestaGenericaRs respuesta = new RespuestaGenericaRs();
        if (existente != null) {
            existente.setEstado("DEVUELTO");
            existente.setFechaEntrega(LocalDate.now());
            prestamoRepository.save(existente);
            respuesta.setMessage("Préstamo entregado correctamente.");
        } else {
            respuesta.setMessage("Error: No se encontró el préstamo.");
        }

        return respuesta;
    }
}


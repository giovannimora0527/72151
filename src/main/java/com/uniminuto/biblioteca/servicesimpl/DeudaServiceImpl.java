package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.ActualizarDeudaRq;
import com.uniminuto.biblioteca.model.DeudaRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.DeudaRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.DeudaService;
import java.math.BigDecimal;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.model.DeudaRq;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
/**
 * Implementación del servicio para manejo de Deudas.
 * 
 * @author Santiago
 */
/**
 * Implementación del servicio para manejo de Deudas.
 * 
 * @author Santiago
 */
@Service
public class DeudaServiceImpl implements DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Deuda> obtenerListadoDeudas() {
        // Cambié al método que existe en el repo
        return deudaRepository.findAllByOrderByIdDeudaDesc();
    }

    @Override
    public Deuda obtenerDeudaPorId(Integer idDeuda) throws BadRequestException {
        Optional<Deuda> optDeuda = deudaRepository.findById(idDeuda);
        if (!optDeuda.isPresent()) {
            throw new BadRequestException("No se encuentra la deuda con el id " + idDeuda);
        }
        return optDeuda.get();
    }

    @Override
public DeudaRs guardarDeuda(DeudaRq deudaRq) throws BadRequestException {
    // Verificar que exista el préstamo
    Prestamo prestamo = prestamoRepository.findById(deudaRq.getIdPrestamo())
            .orElseThrow(() -> new BadRequestException("No existe el préstamo con id " + deudaRq.getIdPrestamo()));

    // Validar que no exista ya una deuda para ese préstamo
    Optional<Deuda> deudaExistente = deudaRepository.findByPrestamo(prestamo);
    if (deudaExistente.isPresent()) {
        throw new BadRequestException("Ya existe una deuda asociada a ese préstamo.");
    }

    // Validar que el préstamo tenga fecha de entrega
    if (prestamo.getFechaEntrega() == null) {
        throw new BadRequestException("El préstamo no tiene fecha de entrega registrada.");
    }

    // Calcular la multa
    BigDecimal multaFija = new BigDecimal("20000");
    BigDecimal multaDiaria = new BigDecimal("1000");
    BigDecimal valorDeuda = multaFija;

    // Calcular días de retraso (usando fecha de entrega vs fecha de devolución)
    LocalDate fechaEntrega = prestamo.getFechaEntrega().toLocalDate();
    LocalDate fechaDevolucion = prestamo.getFechaDevolucion();

    if (fechaEntrega.isAfter(fechaDevolucion)) {
        long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucion, fechaEntrega);
        BigDecimal multaRetraso = multaDiaria.multiply(BigDecimal.valueOf(diasRetraso));
        valorDeuda = valorDeuda.add(multaRetraso);
    }

    // Crear la deuda
    Deuda deuda = new Deuda();
    deuda.setPrestamo(prestamo);
    deuda.setUsuario(prestamo.getUsuario());
    deuda.setValorDeuda(valorDeuda);
    deuda.setMultaFija(multaFija);
    deuda.setEstadoPago(Deuda.EstadoPago.NO_CANCELADO);
    deuda.setFechaGeneracion(LocalDate.now());
    deuda.setFechaLimitePago(LocalDate.now().plusDays(30));
    deuda.setEstado(true);

    deudaRepository.save(deuda);

    return transformarDeudaToDeudaRs(deuda, "La deuda se ha registrado correctamente.");
}


    private DeudaRs transformarDeudaToDeudaRs(Deuda deuda, String message) {
        DeudaRs rs = new DeudaRs();

        rs.setIdDeuda(deuda.getIdDeuda());
        rs.setIdPrestamo(deuda.getPrestamo().getIdPrestamo());
        rs.setIdUsuario(deuda.getUsuario().getIdUsuario());
        rs.setNombreUsuario(deuda.getUsuario().getNombre());
        rs.setIdLibro(deuda.getPrestamo().getLibro().getIdLibro());
        rs.setTituloLibro(deuda.getPrestamo().getLibro().getTitulo());
        rs.setValorDeuda(deuda.getValorDeuda());
        rs.setMultaFija(deuda.getMultaFija());
        rs.setEstadoPago(deuda.getEstadoPago());
        rs.setFechaPago(deuda.getFechaPago());
        rs.setMetodoPago(deuda.getMetodoPago());
        rs.setFechaGeneracion(deuda.getFechaGeneracion());
        rs.setFechaLimitePago(deuda.getFechaLimitePago());
        rs.setEstado(deuda.getEstado());
        rs.setMessage(message);

        return rs;
    }

    @Transactional
    @Override
public RespuestaGenericaRs actualizarDeuda(ActualizarDeudaRq actualizarRq) throws BadRequestException {
    Optional<Deuda> optDeuda = deudaRepository.findById(actualizarRq.getIdDeuda());
    if (!optDeuda.isPresent()) {
        throw new BadRequestException("No se encontró la deuda con el ID proporcionado.");
    }

    Deuda deudaActual = optDeuda.get();

    // Actualizar únicamente los campos permitidos
    deudaActual.setMetodoPago(actualizarRq.getMetodoPago());
    deudaActual.setFechaPago(actualizarRq.getFechaPago());
    deudaActual.setEstadoPago(Deuda.EstadoPago.CANCELADO); // Si llega pago, se cancela automáticamente
    deudaActual.setEstado(true); // Se mantiene activa

    deudaRepository.saveAndFlush(deudaActual);

    RespuestaGenericaRs rta = new RespuestaGenericaRs();
    rta.setMessage("Se ha actualizado el pago de la deuda correctamente.");
    return rta;
}
}



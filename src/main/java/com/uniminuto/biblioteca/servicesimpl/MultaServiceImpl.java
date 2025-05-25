package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.EstadoMulta;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Multa;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.MultaRq;
import com.uniminuto.biblioteca.services.MultaService;
import com.uniminuto.biblioteca.repository.MultaRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.repository.LibroRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class MultaServiceImpl implements MultaService {
    
    @Autowired
    private MultaRepository multaRepository;
    
    private int calcularMontoMulta(Prestamo prestamo) {
        LocalDateTime fechaLimite = prestamo.getFechaDevolucion();
        LocalDateTime fechaEntrega = prestamo.getFechaEntrega();

        if (fechaLimite == null || fechaEntrega == null) {
            throw new IllegalArgumentException("Las fechas de devolución y entrega no pueden ser nulas.");
        }

        long diasRetraso = Duration.between(fechaLimite, fechaEntrega).toDays();

        if (diasRetraso <= 0) {
            return 0; // No hay retraso
        }

        int valorPorDia = 1000;
        return (int) (diasRetraso * valorPorDia);
    }

    @Override
    public List<Multa> listarMultas() throws BadRequestException {
        return this.multaRepository.findAllByOrderByFechaMultaDesc();
    }
    
    @Override
    public Multa listarMultaPorId(Integer multaId) throws BadRequestException {
        Optional<Multa> optMulta = this.multaRepository.findById(multaId);
        if (!optMulta.isPresent()) {
            throw new BadRequestException("No se encuentra la multa con el id = "
                    + multaId);
        }
        return optMulta.get();
    }
    
    @Override
    public void guardarMulta(Prestamo prestamo) {
        
        // Verificar si ya hay multa para este préstamo
        Optional<Multa> optMult = multaRepository.findByPrestamo(prestamo);
        if (optMult.isPresent()) {
            // Ya existe multa, no crear de nuevo 
            return;
        }
        
        // Calculamos el monto
        Integer montoMulta = calcularMontoMulta(prestamo);
        if (montoMulta <= 0) {
            return; // No se crea multa si no hubo retraso
        }
        
        Multa nuevaMulta = new Multa();
        
        nuevaMulta.setUsuario(prestamo.getUsuario());
        nuevaMulta.setLibro(prestamo.getLibro());
        nuevaMulta.setPrestamo(prestamo);
        nuevaMulta.setConcepto("Entrega tardía");
        nuevaMulta.setMonto(montoMulta);
        nuevaMulta.setEstado(EstadoMulta.PENDIENTE);
        nuevaMulta.setFechaMulta(prestamo.getFechaEntrega());
        //nuevaMulta.setFechaMulta(LocalDateTime.now());

        multaRepository.save(nuevaMulta);
    }
    
    @Override
    public RespuestaGenericaRs actualizarMulta(Multa multa) throws BadRequestException {
        
        Optional<Multa> optMul = this.multaRepository.findById(multa.getIdMulta());
        if (!optMul.isPresent()) {
            throw new BadRequestException("No existe la multa.");
        }
        
        Multa multaActual = optMul.get();
        
        // fechaPago NO puede ser anterior a la fecha de multa
        if(multa.getFechaPago().isBefore(multaActual.getFechaMulta())){
            throw new BadRequestException(
            "La fecha de pago no puede ser anterior a la fecha de multa.");
        }
        
        multaActual.setFechaPago(multa.getFechaPago());
        multaActual.setEstado(EstadoMulta.PAGADO);

        this.multaRepository.save(multaActual);
        
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Fecha de pago y estado de la multa actualizados correctamente.");
        
        return rta;
    }

}
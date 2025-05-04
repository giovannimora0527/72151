package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import com.uniminuto.biblioteca.services.UsuarioService;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.repository.LibroRepository;
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
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        return this.prestamoRepository.findAllByOrderByFechaPrestamoDesc();
    }
    
    @Override
    public Prestamo listarPrestamoPorId(Integer prestamoId) throws BadRequestException {
        Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamoId);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el prestamo con el id = "
                    + prestamoId);
        }
        return optPrestamo.get();
    }
    
    @Override
    public RespuestaGenericaRs guardarPrestamo(PrestamoRq prestamo) throws BadRequestException {
        Prestamo prestamoToSave = this.transformarPrestamoRqToPrestamo(prestamo);
        this.prestamoRepository.save(prestamoToSave);

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("El prestamo se ha creado satisfactoriamente.");
        return rta;
    }
    
    private Prestamo transformarPrestamoRqToPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(prestamoRq.getUsuarioId());
        Libro libro = this.libroService.obtenerLibroId(prestamoRq.getLibroId());
        
        if(libro.getExistencias()<= 0) {
            throw new BadRequestException("No hay copias disponibles para préstamo.");
        }
        
        if (!prestamoRq.getFechaDevolucion().isAfter(prestamoRq.getFechaPrestamo().plusDays(0))) {
            throw new BadRequestException("La fecha de devolución debe ser al menos un día despues de la de préstamo.");
        }
        
        Prestamo p = new Prestamo();
        
        // p.setFechaPrestamo(LocalDateTime.now()); Por si acaso
        p.setFechaPrestamo(prestamoRq.getFechaPrestamo());
        p.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        p.setLibro(libro);
        p.setUsuario(usuario);
        p.setFechaEntrega(null); // Sabemos que en principio es nulo
        p.setEstado(EstadoPrestamo.PRESTADO); // Forzamos el valor por defecto de estado
        
        return p;
    }
    
    @Override
    public RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        
        Optional<Prestamo> optPres = this.prestamoRepository.findById(prestamo.getIdPrestamo());
        if (!optPres.isPresent()) {
            throw new BadRequestException("No existe el prestamo.");
        }
        
        Prestamo prestamoActual = optPres.get();
        
        // fechaEntrega NO puede ser anterior a la fecha de préstamo
        if(prestamo.getFechaEntrega().isBefore(prestamoActual.getFechaPrestamo())){
            throw new BadRequestException(
            "La fecha de entrega no puede ser anterior a la fecha de préstamo.");
        }
        
        // fechaEntrega debe ser al menos un día después de la fecha de préstamo
        if (!prestamo.getFechaEntrega().isAfter(prestamoActual.getFechaPrestamo().plusDays(1))) {
        throw new BadRequestException(
            "La fecha de entrega debe ser al menos un día después de la fecha de préstamo.");
        }
        
        prestamoActual.setFechaEntrega(prestamo.getFechaEntrega());
        
        if(prestamo.getFechaEntrega().isBefore(prestamoActual.getFechaDevolucion())) {
            prestamoActual.setEstado(EstadoPrestamo.DEVUELTO);
        } else {
            prestamoActual.setEstado(EstadoPrestamo.VENCIDO);
        }

        this.prestamoRepository.save(prestamoActual);
        
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Fecha de entrega y estado del préstamo actualizados correctamente.");
        
        return rta;
    }

}
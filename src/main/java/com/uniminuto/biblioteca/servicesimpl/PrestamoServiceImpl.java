package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.model.PrestamoDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.services.UsuarioService;
import com.uniminuto.biblioteca.services.PrestamoService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lmora
 */
@Service
public class PrestamoServiceImpl  implements PrestamoService  {

    @Autowired
    private PrestamoRepository prestamoRepository;
    @Autowired
    private LibroService libroService;
    @Autowired
    private UsuarioService usuarioService;


    @Override
    @Transactional
    public List<PrestamoDto> listarPrestamos() {
        List<Prestamo> prestamos = prestamoRepository.findAll();
        
        prestamos.forEach(prestamo -> {
            EstadoPrestamo estadoOriginal = prestamo.getEstado();
            prestamo.calcularEstado(); // Forzar cálculo
            
            if (prestamo.getEstado() != estadoOriginal) {
                prestamoRepository.save(prestamo); // Update en BD
            }
        });
    
        return prestamos.stream()
            .map(this::convertirAResumenDTO)
            .collect(Collectors.toList());
    }

    private PrestamoDto convertirAResumenDTO(Prestamo prestamo) {
        PrestamoDto dto = new PrestamoDto();
        dto.setIdPrestamo(prestamo.getIdPrestamo());
        dto.setUsuario(prestamo.getUsuario().getNombre());
        dto.setLibro(prestamo.getLibro().getTitulo());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucion(prestamo.getFechaDevolucion());
        dto.setFechaEntrega(prestamo.getFechaEntrega());
        dto.setEstado(prestamo.getEstado().name());
        return dto;
    }

    @Override
    public RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Validar que el usuario existe
        if (!usuarioService.existeUsuario(prestamoRq.getIdUsuario())) {
            throw new BadRequestException("Usuario no encontrado.");
        }

        // Validar disponibilidad del libro
        if (!libroService.estaDisponible(prestamoRq.getIdLibro())) {
            throw new BadRequestException("Libro no disponible");
        }

        // Validar fecha de devolución
        LocalDate fechaDevolucion = prestamoRq.getFechaDevolucion();
        if (fechaDevolucion.isBefore(LocalDate.now().plusDays(1))) {
            throw new BadRequestException("La fecha de devolución debe ser al menos un día después de hoy.");
        }

        // Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuarioService.obtenerUsuarioPorId(prestamoRq.getIdUsuario()));
        prestamo.setLibro(libroService.obtenerLibroId(prestamoRq.getIdLibro()));
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setFechaPrestamo(LocalDateTime.now());

        prestamoRepository.save(prestamo);

        return new RespuestaGenericaRs("Préstamo creado exitosamente.");
    }
}

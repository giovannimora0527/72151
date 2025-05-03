package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Prestamo> obtenerListadoPrestamos() {
        List<Prestamo> prestamos = this.prestamoRepository.findAllByOrderByFechaPrestamoDesc();
        prestamos.forEach(this::verificarYActualizarEstadoVencido);
        return prestamos;
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException {
        Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamoId);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el préstamo con el id " + prestamoId);
        }

        Prestamo prestamo = optPrestamo.get();
        verificarYActualizarEstadoVencido(prestamo);

        return prestamo;
    }

    @Override
    public PrestamoRs guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        
        // Asignar fecha actual si no se proporciona
    if (prestamoRq.getFechaPrestamo() == null) {
        prestamoRq.setFechaPrestamo(LocalDate.now());
    }

    // Asignar estado PRESTADO si no se proporciona
    if (prestamoRq.getEstado() == null) {
        prestamoRq.setEstado(EstadoPrestamo.PRESTADO);
    }

        if (prestamoRq.getFechaPrestamo().isAfter(prestamoRq.getFechaDevolucion())) {
            throw new BadRequestException("La fecha de devolución debe ser posterior a la fecha de préstamo.");
        }

        if (prestamoRq.getFechaPrestamo().isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha de préstamo no puede ser en el pasado.");
        }

        if (prestamoRq.getFechaDevolucion().isAfter(prestamoRq.getFechaPrestamo().plusDays(30))) {
            throw new BadRequestException("El préstamo no puede exceder los 30 días.");
        }

        Optional<Libro> libro = libroRepository.findById(prestamoRq.getIdLibro());
        if (!libro.isPresent() || libro.get().getExistencias() <= 0) {
            throw new BadRequestException("No hay ejemplares disponibles para préstamo.");
        }

        Prestamo prestamoToSave = this.transformarPrestamoRqToPrestamo(prestamoRq);
        Optional<Prestamo> prestamoExistente = this.prestamoRepository.findByUsuarioAndLibroAndEstadoNot(
            prestamoToSave.getUsuario(),
            prestamoToSave.getLibro(),
            EstadoPrestamo.DEVUELTO
        );

        if (prestamoExistente.isPresent()) {
            throw new BadRequestException("Ya existe un préstamo activo para este usuario y libro.");
        }

        Libro libroToUpdate = libro.get();
        libroToUpdate.setExistencias(libroToUpdate.getExistencias() - 1);
        libroRepository.save(libroToUpdate);

        this.prestamoRepository.save(prestamoToSave);

        PrestamoRs rta = new PrestamoRs();
        rta.setIdPrestamo(prestamoToSave.getIdPrestamo());
        rta.setIdUsuario(prestamoToSave.getUsuario().getIdUsuario());
        rta.setNombreUsuario(prestamoToSave.getUsuario().getNombre());
        rta.setIdLibro(prestamoToSave.getLibro().getIdLibro());
        rta.setTituloLibro(prestamoToSave.getLibro().getTitulo());
        rta.setFechaPrestamo(prestamoToSave.getFechaPrestamo());
        rta.setFechaDevolucion(prestamoToSave.getFechaDevolucion());
        rta.setEstado(prestamoToSave.getEstado());
        rta.setMessage("El préstamo se ha registrado correctamente.");

        return rta;
    }

    @Transactional
@Override
public RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {

    Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamo.getIdPrestamo());

    if (!optPrestamo.isPresent()) {
        throw new BadRequestException("No se encontró el préstamo con el ID proporcionado.");
    }

    Prestamo prestamoActual = optPrestamo.get();

    if (prestamo.getUsuario() == null || prestamo.getLibro() == null) {
        throw new BadRequestException("El préstamo debe incluir información completa de usuario y libro.");
    }

    if (!prestamoActual.getUsuario().getIdUsuario().equals(prestamo.getUsuario().getIdUsuario()) ||
        !prestamoActual.getLibro().getIdLibro().equals(prestamo.getLibro().getIdLibro())) {
        throw new BadRequestException("No se puede modificar el libro o usuario de un préstamo.");
    }

    // Validación: Fecha de devolución no puede ser anterior a la de préstamo
    if (prestamo.getFechaDevolucion() != null &&
        prestamoActual.getFechaPrestamo() != null &&
        prestamo.getFechaDevolucion().isBefore(prestamoActual.getFechaPrestamo().toLocalDate())) {
        throw new BadRequestException("La fecha de devolución no puede ser anterior a la fecha del préstamo.");
    }

    // ✅ Nueva validación: Fecha de entrega no puede ser anterior a la fecha de préstamo
    if (prestamo.getFechaEntrega() != null &&
        prestamoActual.getFechaPrestamo() != null &&
        prestamo.getFechaEntrega().toLocalDate().isBefore(prestamoActual.getFechaPrestamo().toLocalDate())) {
        throw new BadRequestException("La fecha de entrega no puede ser anterior a la fecha del préstamo.");
    }

    // Si se marca como DEVUELTO sin fecha, se asigna fecha actual
    if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO && prestamo.getFechaDevolucion() == null) {
        prestamo.setFechaDevolucion(LocalDate.now());
    }

    RespuestaGenericaRs rta = new RespuestaGenericaRs();
    rta.setMessage("Se ha actualizado el registro satisfactoriamente");

    if (!compararObjetosPrestamoActualizar(prestamoActual, prestamo)) {
        return rta;
    }

    // Si el estado cambia a DEVUELTO, se incrementa la existencia del libro
    if (prestamoActual.getEstado() != EstadoPrestamo.DEVUELTO && prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
        Libro libroToUpdate = prestamoActual.getLibro();
        libroToUpdate.setExistencias(libroToUpdate.getExistencias() + 1);
        libroRepository.save(libroToUpdate);
    }

    // Se actualizan los campos permitidos
    prestamoActual.setFechaEntrega(prestamo.getFechaEntrega());
    prestamoActual.setFechaDevolucion(prestamo.getFechaDevolucion());
    prestamoActual.setEstado(prestamo.getEstado());

    this.prestamoRepository.saveAndFlush(prestamoActual);

    return rta;
}



    private boolean compararObjetosPrestamoActualizar(Prestamo actual, Prestamo nuevo) {
    return !Objects.equals(actual.getFechaDevolucion(), nuevo.getFechaDevolucion()) ||
           !Objects.equals(actual.getFechaEntrega(), nuevo.getFechaEntrega()) ||
           !Objects.equals(actual.getEstado(), nuevo.getEstado());
}


    private Prestamo transformarPrestamoRqToPrestamo(PrestamoRq rq) throws BadRequestException {
        Prestamo p = new Prestamo();

        Usuario usuario = usuarioRepository.findById(rq.getIdUsuario()).orElse(null);
        if (usuario == null) {
            throw new BadRequestException("El usuario con id " + rq.getIdUsuario() + " no existe.");
        }

        Libro libro = libroRepository.findById(rq.getIdLibro()).orElse(null);
        if (libro == null) {
            throw new BadRequestException("El libro con id " + rq.getIdLibro() + " no existe.");
        }

        p.setUsuario(usuario);
        p.setLibro(libro);
        p.setFechaPrestamo(rq.getFechaPrestamo().atStartOfDay());
        p.setFechaDevolucion(rq.getFechaDevolucion());
        p.setEstado(EstadoPrestamo.PRESTADO);
        
        System.out.println("Usuario asignado: " + p.getUsuario());
        System.out.println("Libro asignado: " + p.getLibro());
        return p;
    }

    private void verificarYActualizarEstadoVencido(Prestamo prestamo) {
        if (prestamo.getEstado() != EstadoPrestamo.DEVUELTO && prestamo.getFechaDevolucion() != null) {
            if (prestamo.getFechaDevolucion().isBefore(LocalDate.now())) {
                prestamo.setEstado(EstadoPrestamo.VENCIDO);
                prestamoRepository.save(prestamo);
            }
        }
    }
}



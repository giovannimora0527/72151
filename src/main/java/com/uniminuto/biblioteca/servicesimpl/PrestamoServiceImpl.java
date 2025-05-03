package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.*;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de préstamos.
 * Proporciona la lógica de negocio para operaciones CRUD y validaciones sobre préstamos.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    /**
     * Repositorio para operaciones sobre la entidad Prestamo.
     */
    @Autowired
    private PrestamoRepository prestamoRepository;

    /**
     * Repositorio para operaciones sobre la entidad Usuario.
     */
    @Autowired
    public UsuarioRepository usuarioRepository;

    /**
     * Servicio para la gestión de libros.
     */
    @Autowired
    private LibroService libroService;

    /**
     * Repositorio para operaciones sobre la entidad Libro.
     */
    @Autowired
    public LibroRepository libroRepository;

    /**
     * Lista todos los préstamos registrados en la base de datos.
     *
     * @return Lista de préstamos.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        return this.prestamoRepository.findAll();
    }

    /**
     * Obtiene un préstamo por su identificador.
     *
     * @param prestamoId Identificador del préstamo.
     * @return El préstamo encontrado.
     * @throws BadRequestException si no se encuentra el préstamo.
     */
    @Override
    public Prestamo obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException {
        Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamoId);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + prestamoId);
        }
        return optPrestamo.get();
    }

    /**
     * Crea un nuevo préstamo en la base de datos.
     *
     * @param prestamoRq Objeto de solicitud con los datos del préstamo a crear.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si las fechas no son válidas o faltan datos.
     */
    @Override
    public RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaPrestamo = prestamoRq.getFechaPrestamo() != null ? prestamoRq.getFechaPrestamo() : fechaActual;

        // 1. Validar que la fecha de préstamo no sea anterior a la fecha actual
        if (fechaPrestamo.isBefore(fechaActual)) {
            throw new BadRequestException("La fecha de préstamo no puede ser anterior a la fecha actual");
        }

        // 2. Validar que la fecha de devolución sea posterior a la fecha de préstamo
        if (prestamoRq.getFechaDevolucion() == null ||
            !prestamoRq.getFechaDevolucion().isAfter(fechaPrestamo)) {
            throw new BadRequestException("La fecha de devolución debe ser al menos 1 dia despues de la fecha actual");
        }

        Prestamo prestamoGuardar = this.convertirPrestamoRqToPrestamo(prestamoRq);

        // Guardar en la base de datos
        this.prestamoRepository.save(prestamoGuardar);

        var libroOpt = libroRepository.findById(prestamoRq.getLibroId());
        // Restar 1 a las existencias de libro
        Integer nuevaExistencia = libroOpt.get().getExistencias() - 1;
        libroService.actualizarExistencias(libroOpt.get().getIdLibro(), nuevaExistencia);

        // Preparar respuesta
        RespuestaGenericaRs respuesta = new RespuestaGenericaRs();
        respuesta.setMessage("Préstamo creado exitosamente");
        return respuesta;
    }

    /**
     * Convierte un objeto PrestamoRq a una entidad Prestamo.
     *
     * @param prestamoRq Objeto de solicitud.
     * @return Entidad Prestamo.
     * @throws BadRequestException si el usuario o libro no existen.
     */
    private Prestamo convertirPrestamoRqToPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(prestamoRq.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());

        Optional<Usuario> optUsu = this.usuarioRepository.findById(prestamoRq.getUsuarioId());
        if (!optUsu.isPresent()) {
            throw new BadRequestException("No existe el Usuario");
        }
        Optional<Libro> optLib = this.libroRepository.findById(prestamoRq.getLibroId());
        if (!optLib.isPresent()) {
            throw new BadRequestException("No existe el Libro");
        }

        Usuario usuario = optUsu.get();
        Libro libro = optLib.get();

        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion( prestamoRq.getFechaDevolucion() );
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setEstado("PRESTADO");
        prestamo.setFechaEntrega(null);

        return prestamo;
    }

    /**
     * Actualiza la información de un préstamo existente.
     *
     * @param prestamo Objeto Prestamo con los datos actualizados.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si el préstamo no existe o las fechas no son válidas.
     */
    @Override
    public RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {

        Optional<Prestamo> optUser = this.prestamoRepository
                .findById(prestamo.getPrestamoId());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el Prestamo.");
        }

        // Validar que la fecha de entrega no sea anterior a la fecha de préstamo
        if (prestamo.getFechaEntrega().isBefore(prestamo.getFechaPrestamo())) {
            throw new BadRequestException("La fecha de entrega del libro no puede ser anterior a la fecha del prestamo");
        }

        Prestamo prestamoActual = optUser.get();
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente");
        if (!compararObjetosUsuarioActualizar(prestamoActual, prestamo)) {
            return rta;
        }

        prestamoActual.setFechaPrestamo(prestamo.getFechaPrestamo());
        prestamoActual.setFechaDevolucion(prestamo.getFechaDevolucion());
        prestamoActual.setLibro(prestamo.getLibro());
        prestamoActual.setUsuario(prestamo.getUsuario());
        prestamoActual.setEstado(prestamo.getEstado());
        prestamoActual.setFechaEntrega(prestamo.getFechaEntrega());

        var libroOpt = libroRepository.findById(prestamo.getLibro().getIdLibro());
        Integer nuevaExistencia = libroOpt.get().getExistencias() + 1;
        libroService.actualizarExistencias(libroOpt.get().getIdLibro(), nuevaExistencia);

        this.prestamoRepository.save(prestamoActual);
        return rta;
    }

    /**
     * Compara dos objetos Prestamo para determinar si hay cambios relevantes.
     *
     * @param prestamoActual Objeto Prestamo actual en la base de datos.
     * @param prestamoFront Objeto Prestamo recibido para actualizar.
     * @return true si hay diferencias, false si son iguales.
     */
    private boolean compararObjetosUsuarioActualizar(Prestamo prestamoActual, Prestamo prestamoFront) {
        return !prestamoActual.getFechaPrestamo().equals(prestamoFront.getFechaPrestamo())
                || !prestamoActual.getFechaDevolucion().equals(prestamoFront.getFechaDevolucion())
                || !prestamoActual.getLibro().equals(prestamoFront.getLibro())
                || !prestamoActual.getEstado().equals(prestamoFront.getEstado())
                || !java.util.Objects.equals(prestamoActual.getFechaEntrega(), prestamoFront.getFechaEntrega())
                || !prestamoActual.getUsuario().equals(prestamoFront.getUsuario());
    }
}

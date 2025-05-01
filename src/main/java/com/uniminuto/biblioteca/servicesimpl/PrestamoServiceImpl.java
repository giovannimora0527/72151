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

@Service
public class PrestamoServiceImpl implements PrestamoService {


    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    private LibroService libroService; // Inyección del servicio

    @Autowired
    public LibroRepository libroRepository;

    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        return this.prestamoRepository.findAll();
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Integer prestamoId) throws BadRequestException {
        Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamoId);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + prestamoId);
        }
        return optPrestamo.get();
    }

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
            throw new BadRequestException("La fecha de devolución debe ser posterior a la fecha de préstamo");
        }

        var libroOpt = libroRepository.findById(prestamoRq.getLibroId());

        Prestamo prestamoGuardar = this.convertirPrestamoRqToPrestamo(prestamoRq);

        // Guardar en la base de datos
        this.prestamoRepository.save(prestamoGuardar);

        // Restar 1 a las existencias de libro
        Integer nuevaExistencia = libroOpt.get().getExistencias() - 1;
        libroService.actualizarExistencias(libroOpt.get().getIdLibro(), nuevaExistencia);

        // Preparar respuesta
        RespuestaGenericaRs respuesta = new RespuestaGenericaRs();
        respuesta.setMessage("Préstamo creado exitosamente");
        return respuesta;
    }

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

        prestamo.setFechaPrestamo(prestamoRq.getFechaPrestamo());
        prestamo.setFechaDevolucion( prestamoRq.getFechaDevolucion() );
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setEstado("PRESTADO");
        prestamo.setFechaEntrega(null);

        return prestamo;
    }

    @Override
    public RespuestaGenericaRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        return null;
    }
}

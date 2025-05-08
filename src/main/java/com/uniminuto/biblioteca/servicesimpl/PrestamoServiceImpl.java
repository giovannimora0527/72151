package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestión de préstamos de la biblioteca
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Obtiene todos los préstamos registrados
     * @return Lista de objetos PrestamoRs con la información de cada préstamo
     */
    @Override
    public List<PrestamoRs> getPrestamos() {
        return prestamoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Registra un nuevo préstamo en el sistema
     * @param rq Objeto con la información del préstamo a crear
     * @return Objeto PrestamoRs con la información del préstamo creado
     */
    @Override
    @Transactional
    public PrestamoRs guardarPrestamo(PrestamoRq rq) {
        // Validar que existan el usuario y el libro
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(rq.getIdUsuario());
        Optional<Libro> libroOpt = libroRepository.findById(rq.getIdLibro());

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        
        if (libroOpt.isEmpty()) {
            throw new RuntimeException("Libro no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        Libro libro = libroOpt.get();

        // Verificar la disponibilidad del libro
        // Contar cuántos préstamos activos hay de este libro
        long prestamosActivos = prestamoRepository.countByLibroAndEstadoIn(
                libro, 
                List.of(Prestamo.EstadoPrestamo.PRESTADO, Prestamo.EstadoPrestamo.VENCIDO)
        );
        
        // Si el número de préstamos activos es igual o mayor a las existencias, no está disponible
        if (prestamosActivos >= libro.getExistencias()) {
            throw new RuntimeException("El libro no está disponible para préstamo.");
        }

        // Validar que la fecha de devolución sea al menos un día después de la fecha actual
        LocalDate fechaActual = LocalDate.now();
        if (rq.getFechaDevolucion() == null || 
            rq.getFechaDevolucion().isBefore(fechaActual.plusDays(1))) {
            throw new RuntimeException("La fecha de devolución debe ser al menos un día posterior a la fecha actual.");
        }

        // Crear el nuevo préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaActual); // Fecha automática
        prestamo.setFechaDevolucion(rq.getFechaDevolucion());
        prestamo.setEstado(Prestamo.EstadoPrestamo.PRESTADO); // Estado inicial siempre es PRESTADO

        return mapToResponse(prestamoRepository.save(prestamo));
    }

    /**
     * Actualiza un préstamo existente
     * Según el requerimiento, solo debe permitir actualizar la fecha de entrega
     * @param id ID del préstamo a actualizar
     * @param rq Objeto con la información del préstamo a actualizar
     * @return Objeto PrestamoRs con la información del préstamo actualizado
     */
    @Override
    @Transactional
    public PrestamoRs actualizarPrestamo(Long id, PrestamoRq rq) {
        // Buscar el préstamo existente
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);
        
        if (prestamoOpt.isEmpty()) {
            throw new RuntimeException("Préstamo no encontrado.");
        }
        
        Prestamo prestamo = prestamoOpt.get();
        
        // Actualizar fecha de entrega (único campo editable según requerimiento)
        if (rq.getFechaEntrega() != null) {
            // Validar que la fecha de entrega no sea anterior a la fecha de préstamo
            if (rq.getFechaEntrega().isBefore(prestamo.getFechaPrestamo())) {
                throw new RuntimeException("La fecha de entrega no puede ser anterior a la fecha de préstamo.");
            }
            
            prestamo.setFechaEntrega(rq.getFechaEntrega());
            
            // Actualizar estado según la fecha de entrega
            // Si la fecha de entrega es posterior a la fecha de devolución, estado = VENCIDO
            // De lo contrario, estado = DEVUELTO
            if (rq.getFechaEntrega().isAfter(prestamo.getFechaDevolucion())) {
                prestamo.setEstado(Prestamo.EstadoPrestamo.VENCIDO);
            } else {
                prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
            }
        }
        
        return mapToResponse(prestamoRepository.save(prestamo));
    }

    /**
     * Obtiene la lista de libros disponibles para préstamo
     * @return Lista de libros disponibles
     */
    @Override
    public List<Libro> getLibrosDisponibles() {
        // Obtener todos los libros
        List<Libro> todosLosLibros = libroRepository.findAll();
        
        // Filtrar los libros según su disponibilidad
        return todosLosLibros.stream().filter(libro -> {
            // Contar cuántos préstamos activos hay de este libro
            long prestamosActivos = prestamoRepository.countByLibroAndEstadoIn(
                    libro, 
                    List.of(Prestamo.EstadoPrestamo.PRESTADO, Prestamo.EstadoPrestamo.VENCIDO)
            );
            
            // Un libro está disponible si hay más existencias que préstamos activos
            return libro.getExistencias() > prestamosActivos;
        }).collect(Collectors.toList());
    }

    /**
     * Mapea un objeto Prestamo a su representación PrestamoRs
     * @param prestamo Objeto Prestamo a mapear
     * @return Objeto PrestamoRs con la información formateada
     */
    private PrestamoRs mapToResponse(Prestamo prestamo) {
        PrestamoRs rs = new PrestamoRs();
        rs.setIdPrestamo(prestamo.getIdPrestamo());
        rs.setNombreUsuario(prestamo.getUsuario().getNombre());
        rs.setTituloLibro(prestamo.getLibro().getTitulo());
        rs.setFechaPrestamo(prestamo.getFechaPrestamo());
        rs.setFechaDevolucion(prestamo.getFechaDevolucion());
        rs.setFechaEntrega(prestamo.getFechaEntrega());
        rs.setEstado(prestamo.getEstado().toString());
        return rs;
    }
}
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.services.PrestamoService;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDate;
import java.util.List;
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
    private LibroService libroService;     // Inyecta el servicio de Libro (para obtener info del libro y actualizar disponibilidad)

    @Override
    // @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        // Lógica para listar préstamos
        return prestamoRepository.findAll();
    }

    @Override
    // @Transactional
    public RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Lógica para crear un nuevo préstamo
        // 1. Validaciones
        // 2. Buscar Usuario y Libro
        // 3. Validar disponibilidad del libro
        // 4. Validar fecha de devolución
        // 5. Crear entidad Prestamo
        // 6. Asignar datos y estados iniciales (PRESTADO, fechaEntrega=NULL)
        // 7. Guardar Prestamo
        // 8. Opcional: Actualizar disponibilidad Libro
        // 9. Devolver respuesta

        if (prestamoRq == null) {
             throw new BadRequestException("La solicitud de prestamo no puede ser nula.");
        }

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Recibida solicitud para crear prestamo (logica pendiente).");

        return rta;
    }

    @Override
    public RespuestaGenericaRs actualizarPrestamo(Integer idPrestamo, LocalDate fechaEntrega) throws BadRequestException {
        // Lógica para actualizar un préstamo (principalmente para registrar devolución)
        // 1. Validaciones de entrada
        // 2. Buscar el préstamo existente
        // 3. Validar que el préstamo esté activo
        // 4. Validar fecha de entrega
        // 5. Asignar fecha de entrega
        // 6. Determinar y asignar el nuevo estado (DEVUELTO o VENCIDO)
        // 7. Guardar el préstamo actualizado
        // 8. Actualizar disponibilidad del Libro
        // 9. Devolver respuesta

         if (idPrestamo == null || fechaEntrega == null) {
             throw new BadRequestException("El ID del prestamo y la fecha de entrega son obligatorios.");
         }

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Recibida solicitud para actualizar prestamo ID " + idPrestamo + " con fecha de entrega " + fechaEntrega + " (logica pendiente).");

        return rta;
    }

}
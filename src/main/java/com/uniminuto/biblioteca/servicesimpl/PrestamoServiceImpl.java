package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.LibroService;
import com.uniminuto.biblioteca.services.PrestamoService; // **AJUSTE:** Asegúrate de que esta importación exista
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // **AJUSTE:** Importar para manejar formatos específicos si es necesario
import java.time.format.DateTimeParseException; // **AJUSTE:** Importar para manejar error de parsing
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para la gestión de préstamos.
 * Contiene la lógica de negocio para crear y actualizar préstamos.
 * @author lmora
 */
@Service
public class PrestamoServiceImpl implements PrestamoService { // **AJUSTE:** Implementa PrestamoService

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    // **AJUSTE:** Define el formato esperado si el frontend envía "dd-MM-yyyy HH:mm"
    // Si el frontend envía "YYYY-MM-DDTHH:mm" (por defecto de datetime-local), NO necesitas este formatter.
    // Si estás SEGURO de que el frontend envía "dd-MM-yyyy HH:mm", descomenta y usa este formatter
    // al parsear en los métodos crearPrestamo y actualizarPrestamo.
    // private static final DateTimeFormatter CUSTOM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    /**
     * Lista todos los préstamos existentes.
     * @return Lista de entidades Prestamo.
     * @throws BadRequestException Si ocurre un error al listar.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        try {
            // **AJUSTE:** Usar un método del repositorio para encontrar todo MENOS los DEVUELTOS y ARCHIVADOS
            List<Prestamo.EstadoPrestamo> excludedStates = List.of(Prestamo.EstadoPrestamo.ARCHIVADO);
            return prestamoRepository.findByEstadoNotIn(excludedStates);
            // Necesitarás añadir el método findByEstadoNotIn en tu PrestamoRepository.java (interface).
            // Import java.util.List; y java.util.Collection; si aún no lo has hecho.

        } catch (Exception e) {
            throw new BadRequestException("Error al listar préstamos: " + e.getMessage());
        }
}

    /**
     * Crea un nuevo registro de préstamo.
     * Implementa la lógica de negocio para validar y registrar un préstamo.
     * @param prestamoRq Objeto con los datos de la solicitud de préstamo (idusuario, idlibro, fechaDevolucion - como String).
     * @return Respuesta genérica indicando el resultado de la operación.
     * @throws BadRequestException Si la solicitud es inválida o no se cumplen las reglas de negocio.
     */
    @Override // **AJUSTE:** Anotación @Override
    @Transactional
    public RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        RespuestaGenericaRs rta = new RespuestaGenericaRs();

        // 1. Validaciones básicas de la solicitud
        if (prestamoRq == null) {
            throw new BadRequestException("La solicitud de prestamo no puede ser nula.");
        }
        // Asumiendo que PrestamoRq tiene getIdusuario(), getIdlibro(), getFechaDevolucion() (String desde datetime-local "YYYY-MM-DDTHH:mm" o similar)
        if (prestamoRq.getIdusuario() == null || prestamoRq.getIdlibro() == null || prestamoRq.getFechaDevolucion() == null || prestamoRq.getFechaDevolucion().trim().isEmpty()) {
             throw new BadRequestException("Los campos idusuario, idlibro y fechaDevolucion son obligatorios.");
        }

        // 2. Buscar Usuario y Libro
        Optional<Usuario> usuarioOpt = usuarioService.findById(prestamoRq.getIdusuario());
        if (!usuarioOpt.isPresent()) {
            throw new BadRequestException("Usuario con ID " + prestamoRq.getIdusuario() + " no encontrado.");
        }
        Usuario usuario = usuarioOpt.get();

        Libro libro;
        try {
             libro = libroService.obtenerLibroId(prestamoRq.getIdlibro());
        } catch (BadRequestException e) {
             throw new BadRequestException("Error al obtener libro con ID " + prestamoRq.getIdlibro() + ": " + e.getMessage());
        }

        // 3. Validar disponibilidad del libro
        if (libro.getExistencias() <= 0) {
             throw new BadRequestException("El libro '" + libro.getTitulo() + "' no está disponible para préstamo.");
        }

        // **AJUSTE:** Validar y parsear fecha de devolución (ahora con hora desde el frontend)
        LocalDateTime fechaDevolucion; // **AJUSTE:** Cambiado a LocalDateTime
        try {
            // **AJUSTE:** Parsear el string. Si el frontend envía "YYYY-MM-DDTHH:mm", LocalDateTime.parse() funciona directo.
            // Si el frontend envía "dd-MM-yyyy HH:mm", usa el formatter: LocalDateTime.parse(prestamoRq.getFechaDevolucion(), CUSTOM_DATETIME_FORMATTER);
            // Usamos el parseo directo que es común con inputs datetime-local:
            fechaDevolucion = LocalDateTime.parse(prestamoRq.getFechaDevolucion());

        } catch (DateTimeParseException e) { // **AJUSTE:** Capturar excepción específica de parsing
             // **AJUSTE:** Mensaje de error actualizado con el formato esperado por datetime-local
             throw new BadRequestException("Formato de fecha de devolución inválido. Asegúrese de incluir la fecha y la hora (Ej: 2025-05-08T10:30).");
        }

        // 5. Validar fecha de devolución (comparación al menos un día DESPUÉS de HOY)
        // Aunque guardamos hora, la regla de negocio del PDF (Source 7, 32) indica "al menos 1 día después",
        // lo que sugiere una comparación basada solo en el día. Mantenemos esta lógica de validación.
        LocalDate fechaDevolucionSoloDia = fechaDevolucion.toLocalDate(); // **AJUSTE:** Obtiene solo la parte de la fecha para la validación
        LocalDate hoySoloDia = LocalDate.now();
        LocalDate mananaSoloDia = hoySoloDia.plusDays(1);

        if (fechaDevolucionSoloDia.isBefore(mananaSoloDia)) { // La validación sigue siendo solo sobre el día
             throw new BadRequestException("La fecha de devolución (" + fechaDevolucionSoloDia + ") debe ser al menos un día después de la fecha actual (" + hoySoloDia + ").");
        }

        // 6. Crear entidad Prestamo
        Prestamo nuevoPrestamo = new Prestamo();

        // 7. Asignar datos y estados iniciales
        nuevoPrestamo.setUsuario(usuario);
        nuevoPrestamo.setLibro(libro);
        nuevoPrestamo.setFechaPrestamo(LocalDateTime.now()); // Fecha y hora actual del préstamo (del sistema backend)
        // **AJUSTE:** Asignar el LocalDateTime parseado a fechaDevolucion
        nuevoPrestamo.setFechaDevolucion(fechaDevolucion);

        // **AJUSTE:** Inicializar fechaEntrega como null (ahora es LocalDateTime, acepta null)
        nuevoPrestamo.setFechaEntrega(null);

        // *** Usar el enum EstadoPrestamo para asignar el estado ***
        try {
             nuevoPrestamo.setEstado(Prestamo.EstadoPrestamo.valueOf("PRESTADO"));
        } catch (IllegalArgumentException e) {
             throw new BadRequestException("Estado de préstamo 'PRESTADO' no válido. Verifica el enum EstadoPrestamo.");
        }

        // 8. Guardar Prestamo
        Prestamo prestamoGuardado = prestamoRepository.save(nuevoPrestamo);

        // 9. Actualizar disponibilidad Libro
        libro.setExistencias(libro.getExistencias() - 1);
        libroService.save(libro);

        // 10. Devolver respuesta de éxito
        rta.setMessage("Préstamo registrado con éxito para el libro '" + libro.getTitulo() + "'.");

        return rta;
    }

    /**
     * Actualiza un préstamo existente (principalmente para registrar la devolución).
     * Implementa la lógica de negocio para registrar la fecha de entrega (recibida del frontend)
     * y actualizar el estado basado en la comparación con la fecha de devolución esperada.
     * @param idPrestamo ID del préstamo a actualizar.
     * @param fechaEntregaDesdeFrontend String con la fecha y hora de entrega desde el frontend (formatoYYYY-MM-DDTHH:mm).
     * @return Respuesta genérica indicando el resultado de la operación.
     * @throws BadRequestException Si la solicitud es inválida o no se cumplen las reglas de negocio.
     */
    @Override // **AJUSTE:** Anotación @Override
    @Transactional
    // La firma del método ahora coincide con la interfaz actualizada y recibe el string
        public RespuestaGenericaRs archivarPrestamo(Integer idPrestamo) throws BadRequestException {
        RespuestaGenericaRs rta = new RespuestaGenericaRs();

        // 1. Validar entrada
        if (idPrestamo == null) {
            throw new BadRequestException("El ID del prestamo es obligatorio para archivar."); // **AJUSTE:** Mensaje actualizado
        }

        // 2. Buscar el préstamo existente
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(idPrestamo);
        if (!prestamoOpt.isPresent()) {
            throw new BadRequestException("Préstamo con ID " + idPrestamo + " no encontrado.");
        }
        Prestamo prestamo = prestamoOpt.get();

        // 3. Validar que el préstamo no esté ya finalizado (DEVUELTO o ARCHIVADO)
        // **AJUSTE:** Comparar con EstadoPrestamo.ARCHIVADO
        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.ARCHIVADO) {
     // Si ya está archivado, damos un mensaje específico
            throw new BadRequestException("El préstamo con ID " + idPrestamo + " ya ha sido archivado y no puede archivarse nuevamente.");
        } else if (prestamo.getEstado() != Prestamo.EstadoPrestamo.DEVUELTO) {
    // Si no está ARCHIVADO, pero tampoco es DEVUELTO (es PRESTADO o VENCIDO)
            throw new BadRequestException("Solo se pueden archivar préstamos que están en estado DEVUELTO. El estado actual es: " + prestamo.getEstado()); // **AJUSTE:** Mensaje actualizado
        }
        // 4. Marcar el estado como ARCHIVADO
        prestamo.setEstado(Prestamo.EstadoPrestamo.ARCHIVADO); // **AJUSTE:** Establecer el estado ARCHIVADO

        // 5. Guardar el cambio
        prestamoRepository.save(prestamo);

        // 6. Actualizar disponibilidad del Libro si el préstamo no estaba VENCIDO o DEVUELTO
        // Si archivas un préstamo que estaba PRESTADO, el libro debería volver a estar disponible.
        // Si archivas uno VENCIDO, el libro ya debería estar disponible (o tienes otra lógica).
        // Adaptar según tu lógica de negocio si el archivado libera el libro.
        // **AJUSTE:** Si el estado ANTES de archivar era PRESTADO, libera el libro.
         if (prestamo.getEstado() == Prestamo.EstadoPrestamo.PRESTADO) {
             Libro libro = prestamo.getLibro();
             libro.setExistencias(libro.getExistencias() + 1);
             libroService.save(libro);
         }


        // 7. Devolver respuesta de éxito
        rta.setMessage("Préstamo con ID " + idPrestamo + " ha sido archivado."); // **AJUSTE:** Mensaje actualizado

        return rta;
    }
    public RespuestaGenericaRs actualizarPrestamo(Integer idPrestamo, String fechaEntregaDesdeFrontend) throws BadRequestException {

        RespuestaGenericaRs rta = new RespuestaGenericaRs(); // Asegúrate de que rta esté declarado

        // 1. Validaciones de entrada básicas
        if (idPrestamo == null || fechaEntregaDesdeFrontend == null || fechaEntregaDesdeFrontend.trim().isEmpty()) {
            throw new BadRequestException("El ID del prestamo y la fecha de entrega son obligatorios para actualizar.");
        }

        // 2. Buscar el préstamo existente
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(idPrestamo);
        if (!prestamoOpt.isPresent()) {
            throw new BadRequestException("Préstamo con ID " + idPrestamo + " no encontrado.");
        }
        Prestamo prestamo = prestamoOpt.get();

        // 3. Validar que el préstamo esté activo (no DEVUELTO o CANCELADO)
         if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO || (prestamo.getEstado() != null && prestamo.getEstado().name().equals("CANCELADO"))) {
             throw new BadRequestException("El préstamo con ID " + idPrestamo + " ya ha sido finalizado.");
         }

        // **AJUSTE:** Parsear la fecha de entrega recibida del frontend a LocalDateTime
        LocalDateTime fechaEntrega; // **AJUSTE:** Variable para el LocalDateTime parseado
        try {
             // **AJUSTE:** Parsear el string recibido a LocalDateTime.
             // Si el frontend envía "YYYY-MM-DDTHH:mm", LocalDateTime.parse() funciona directo.
             // Si el frontend envía "dd-MM-yyyy HH:mm", usa el formatter: LocalDateTime.parse(fechaEntregaDesdeFrontend, CUSTOM_DATETIME_FORMATTER);
             // Usamos el parseo directo que es común con inputs datetime-local:
             fechaEntrega = LocalDateTime.parse(fechaEntregaDesdeFrontend);

        } catch (DateTimeParseException e) { // **AJUSTE:** Capturar excepción específica de parsing
             throw new BadRequestException("Formato de fecha de entrega inválido. Asegúrese de incluir la fecha y la hora (Ej: 2025-05-08T10:30)."); // **AJUSTE:** Mensaje de error actualizado
        }

        // **AJUSTE:** Validar que la fecha de entrega no sea anterior a la fecha de préstamo
        // Ambos son LocalDateTime ahora, la comparación incluye hora.
        // Asegúrate de que fechaPrestamo en la entidad también sea LocalDateTime para esta comparación.
        if (prestamo.getFechaPrestamo() != null && fechaEntrega.isBefore(prestamo.getFechaPrestamo())) {
             throw new BadRequestException("La fecha de entrega (" + fechaEntrega + ") no puede ser anterior a la fecha de préstamo (" + prestamo.getFechaPrestamo() + ").");
        }


        // **AJUSTE:** Asignar fecha de entrega al préstamo
        prestamo.setFechaEntrega(fechaEntrega); // **AJUSTE:** Asigna el LocalDateTime parseado

        // *** AÑADIR ESTAS LÍNEAS PARA DEPURAR ***
        System.out.println("--- Depuración Actualizar Prestamo ---");
        System.out.println("ID Prestamo: " + idPrestamo);
        System.out.println("Fecha Devolución Esperada (BD): " + prestamo.getFechaDevolucion()); // Esto será LocalDateTime
        System.out.println("Fecha Entrega Recibida y Parseada: " + fechaEntrega); // Esto será el LocalDateTime del input
        System.out.println("¿Fecha Entrega Recibida es DESPUÉS de Fecha Devolución Esperada? " + fechaEntrega.isAfter(prestamo.getFechaDevolucion())); // Compara LocalDateTime con LocalDateTime
        System.out.println("------------------------------------");
        // ************************************


        // 6. Determinar y asignar el nuevo estado (DEVUELTO o VENCIDO)
        // La comparación isAfter con LocalDateTime funciona correctamente con hora.
        // Usamos la fecha parseada del frontend para la comparación.
        // Asegúrate de que fechaDevolucion en la entidad sea LocalDateTime para esta comparación.
        if (prestamo.getFechaDevolucion() != null && fechaEntrega.isAfter(prestamo.getFechaDevolucion())) { // Compara la fecha de entrega recibida con la fecha de devolución esperada (ambos LocalDateTime)
             try {
                 prestamo.setEstado(Prestamo.EstadoPrestamo.valueOf("VENCIDO")); // Si la entrega es DESPUÉS de la devolución esperada
             } catch (IllegalArgumentException e) {
                 throw new BadRequestException("Estado de préstamo 'VENCIDO' no válido. Verifica el enum EstadoPrestamo.");
             }
        } else {
             try {
                 prestamo.setEstado(Prestamo.EstadoPrestamo.valueOf("DEVUELTO")); // Si la entrega es IGUAL o ANTES de la devolución esperada
             } catch (IllegalArgumentException e) {
                 throw new BadRequestException("Estado de préstamo 'DEVUELTO' no válido. Verifica el enum EstadoPrestamo.");
             }
        }

        // 7. Guardar el préstamo actualizado
        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        // 8. Actualizar disponibilidad del Libro
        Libro libro = prestamo.getLibro();
        libro.setExistencias(libro.getExistencias() + 1);
        libroService.save(libro);

        // 9. Devolver respuesta de éxito
        rta.setMessage("Préstamo con ID " + idPrestamo + " actualizado a estado '" + prestamoActualizado.getEstado() + "'.");

        return rta;
    }
}
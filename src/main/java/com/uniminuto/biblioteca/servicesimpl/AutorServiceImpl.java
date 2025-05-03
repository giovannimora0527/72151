package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        this.autorRepository.findByNacionalidad(nacionalidad).forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }

        return listaAutores;
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }

    @Override
    public RespuestaGenericaRs crearAutor(AutorRq autorRq) throws BadRequestException {
        // Verificación si el autor ya existe por nombre o correo
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autorRq.getNombre());
        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya se encuentra registrado con ese nombre. Intente de nuevo.");
        }

        LocalDate hoy = LocalDate.now();
        if (autorRq.getFechaNacimiento() == null || !autorRq.getFechaNacimiento().isBefore(hoy)) {
            throw new BadRequestException("La fecha de nacimiento no puede ser la de hoy ni una fecha futura.");
        }

        // Crear el autor a partir del Request
        Autor autor = new Autor();
        autor.setNombre(autorRq.getNombre());
        autor.setNacionalidad(autorRq.getNacionalidad());
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());

        try {
            // Guardar el autor en la base de datos
            this.autorRepository.save(autor);
            return new RespuestaGenericaRs("Autor creado con éxito");
        } catch (Exception e) {
            // Manejo de errores si la creación falla
            throw new BadRequestException("Error al crear el autor: " + e.getMessage());
        }
    }

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException {
        // Buscar el autor por ID
        Optional<Autor> autorOpt = this.autorRepository.findById(autor.getAutorId());
        if (!autorOpt.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autor.getAutorId());
        }

        LocalDate hoy = LocalDate.now();
        if (autor.getFechaNacimiento() == null || !autor.getFechaNacimiento().isBefore(hoy)) {
            throw new BadRequestException("La fecha de nacimiento no puede ser la de hoy ni una fecha futura.");
        }

        Autor autorActual = autorOpt.get();
        // Verificar si hay cambios en los campos relevantes
        boolean nombreCambiado = !autorActual.getNombre().equals(autor.getNombre());
        boolean nacionalidadCambiada = !Objects.equals(autorActual.getNacionalidad(), autor.getNacionalidad());
        boolean fechaCambiada = !Objects.equals(autorActual.getFechaNacimiento(), autor.getFechaNacimiento());

        if (!nombreCambiado && !nacionalidadCambiada && !fechaCambiada) {
            return new RespuestaGenericaRs("No se realizaron cambios en el autor.");
        }

        // Validar si el nuevo nombre ya existe
        if (nombreCambiado) {
            Optional<Autor> autorConNombre = autorRepository.findByNombre(autor.getNombre());
            if (autorConNombre.isPresent()) {
                throw new BadRequestException("Ya existe un autor con el nombre: " + autor.getNombre());
            }
        }

        // Actualizar los campos del autor
        autorActual.setNombre(autor.getNombre());
        autorActual.setNacionalidad(autor.getNacionalidad());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());

        try {
            // Guardar los cambios en la base de datos
            this.autorRepository.save(autorActual);
            return new RespuestaGenericaRs("Autor actualizado correctamente.");
        } catch (Exception e) {
            // Manejo de errores si la actualización falla
            throw new BadRequestException("Error al actualizar el autor: " + e.getMessage());
        }
    }

}

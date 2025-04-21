package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.Collections;
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
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId)
            throws BadRequestException {
        if (autorId == null) {
            throw new BadRequestException("El id del autor no puede ser vacio.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) {
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return !librosAutor.isEmpty() ? librosAutor : Collections.EMPTY_LIST;
    }

    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        if (nombreLibro.isBlank() || nombreLibro.isEmpty()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de "
                    + nombreLibro + ".");
        }
        return libro;
    }

    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(
            Integer fechaInicio, Integer fechaFin)
            throws BadRequestException {
        if (fechaInicio == null) {
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (fechaFin == null) {
            throw new BadRequestException("La fecha final es obligatoria.");
        }

        if (fechaFin < fechaInicio) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }

        return this.libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);
    }

    @Override
    public RespuestaGenericaRs crearLibro(LibroRq libroRq) throws BadRequestException {
        // Paso 1. - en la bd si el libro existe por nombre
        // Paso 2. SI ESTA => lanzo el error
        // Paso 3. SINO esta Convertir mi objeto entrada rq a entidad Libro
        // Paso 4. Guardo el registro
        // Paso 5. Devolver una respuesta
        if (this.libroRepository.existsByTitulo(libroRq.getTitulo())) {
            throw new BadRequestException("El libro se encuentra ya registrado");
        }

        Libro libroGuardar = this.convertirLibroRqToLibro(libroRq);
        this.libroRepository.save(libroGuardar);
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha guardado el libro satisfactoriamente");
        return rta;
    }

    private Libro convertirLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
        Libro libro = new Libro();
        libro.setAnioPublicacion(libroRq.getAnioPublicacion());
        Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());
        if (autor == null) {
            throw new BadRequestException("No existe el autor con el ID proporcionado.");
        }
        Optional<Categoria> optCat = this.categoriaRepository.findById(libroRq.getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoria con el ID proporcionado.");
        }
        Categoria categoria = optCat.get();
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setTitulo(libroRq.getTitulo());
        libro.setExistencias(libroRq.getExistencias());
        return libro;
    }


    @Override
    public RespuestaGenericaRs actualizarLibro(Libro actualizarLibro) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(actualizarLibro.getIdLibro());
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No existe el libro con el ID proporcionado.");
        }

        Libro libroActual = optLibro.get();

        // Verifica si hay cambios reales
        if (!hayCambiosEnLibro(libroActual, actualizarLibro)) {
            RespuestaGenericaRs rta = new RespuestaGenericaRs();
            rta.setMessage("No se detectaron cambios en el libro.");
            return rta;
        }

        // Si cambió el título, valida si ya existe otro libro con ese título
        if (!libroActual.getTitulo().trim().equalsIgnoreCase(actualizarLibro.getTitulo().trim())
                && this.libroRepository.existsByTitulo(actualizarLibro.getTitulo())) {
            throw new BadRequestException("Ya existe un libro con el título '" + actualizarLibro.getTitulo() + "'.");
        }

        // Valida el autor
        if (actualizarLibro.getAutor() == null || actualizarLibro.getAutor().getAutorId() == null) {
            throw new BadRequestException("Debe especificar un autor válido.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(actualizarLibro.getAutor().getAutorId());
        if (autor == null) {
            throw new BadRequestException("No existe el autor con ID " + actualizarLibro.getAutor().getAutorId());
        }

        // Valida la categoría
        if (actualizarLibro.getCategoria() == null || actualizarLibro.getCategoria().getCategoriaId() == null) {
            throw new BadRequestException("Debe especificar una categoría válida.");
        }
        Optional<Categoria> optCat = this.categoriaRepository.findById(actualizarLibro.getCategoria().getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoría con ID " + actualizarLibro.getCategoria().getCategoriaId());
        }
        Categoria categoria = optCat.get();

        // Actualiza los campos
        libroActual.setTitulo(actualizarLibro.getTitulo());
        libroActual.setAnioPublicacion(actualizarLibro.getAnioPublicacion());
        libroActual.setAutor(autor);
        libroActual.setCategoria(categoria);
        libroActual.setExistencias(actualizarLibro.getExistencias());

        this.libroRepository.save(libroActual);

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el libro satisfactoriamente.");
        return rta;
    }

    private boolean hayCambiosEnLibro(Libro actual, Libro nuevo) {
        if (nuevo.getAutor() == null || actual.getAutor() == null ||
            nuevo.getCategoria() == null || actual.getCategoria() == null) {
            return true; // Si alguna de las relaciones es nula, consideramos que hay cambio
        }
        return !actual.getTitulo().equals(nuevo.getTitulo()) ||
               !Objects.equals(actual.getAnioPublicacion(), nuevo.getAnioPublicacion()) ||
               !actual.getAutor().getAutorId().equals(nuevo.getAutor().getAutorId()) ||
               !actual.getCategoria().getCategoriaId().equals(nuevo.getCategoria().getCategoriaId()) ||
               !actual.getExistencias().equals(nuevo.getExistencias());
    }
}
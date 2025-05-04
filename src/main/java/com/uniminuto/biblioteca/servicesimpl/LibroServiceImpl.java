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
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para la gestión de libros.
 * Proporciona la lógica de negocio para operaciones CRUD y consultas sobre libros.
 * 
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    /**
     * Repositorio para operaciones sobre la entidad Libro.
     */
    @Autowired
    private LibroRepository libroRepository;

    /**
     * Servicio para la gestión de autores.
     */
    @Autowired
    private AutorService autorService;
    
    /**
     * Repositorio para operaciones sobre la entidad Categoria.
     */
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Lista todos los libros registrados en la base de datos.
     *
     * @return Lista de libros.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }

    /**
     * Obtiene un libro por su identificador.
     *
     * @param libroId Id del libro.
     * @return Libro encontrado.
     * @throws BadRequestException si no se encuentra el libro.
     */
    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    /**
     * Obtiene los libros asociados a un autor.
     *
     * @param autorId Id del autor.
     * @return Lista de libros del autor.
     * @throws BadRequestException si el id es nulo o el autor no existe.
     */
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

    /**
     * Obtiene un libro por su nombre.
     *
     * @param nombreLibro Nombre del libro.
     * @return Libro encontrado.
     * @throws BadRequestException si el nombre es inválido o no existe el libro.
     */
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

    /**
     * Obtiene los libros publicados en un rango de años.
     *
     * @param fechaInicio Año inicial.
     * @param fechaFin Año final.
     * @return Lista de libros en el rango.
     * @throws BadRequestException si las fechas son inválidas.
     */
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

    /**
     * Crea un nuevo libro en la base de datos.
     *
     * @param libroRq Objeto de solicitud con los datos del libro a crear.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si el libro ya existe.
     */
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
    
    /**
     * Convierte un objeto LibroRq a una entidad Libro.
     *
     * @param libroRq Objeto de solicitud.
     * @return Entidad Libro.
     * @throws BadRequestException si la categoría no existe.
     */
    private Libro convertirLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
        Libro libro = new Libro();
        libro.setAnioPublicacion(libroRq.getAnioPublicacion());
        Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());

        Optional<Categoria> optCat = this.categoriaRepository.findById(libroRq.getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoria");
        }
        Categoria categoria = optCat.get();
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setTitulo(libroRq.getTitulo());
        libro.setExistencias(libroRq.getExistencias());
        return libro;
    }

    /**
     * Lista los libros con existencias disponibles (existencias >= 1).
     *
     * @return Lista de libros disponibles.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public List<Libro> listarLibrosDisponibles() throws BadRequestException {
        return this.libroRepository.findByExistenciasGreaterThanEqual(1);
    }

    /**
     * Actualiza las existencias de un libro.
     *
     * @param libroId Id del libro.
     * @param nuevasExistencias Nuevo valor de existencias.
     * @return Respuesta genérica del resultado.
     * @throws BadRequestException si el libro no existe o el valor es inválido.
     */
    @Override
    public RespuestaGenericaRs actualizarExistencias(Integer libroId, Integer nuevasExistencias) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = " + libroId);
        }
        if (nuevasExistencias == null || nuevasExistencias < 0) {
            throw new BadRequestException("El valor de existencias no es válido.");
        }
        Libro libro = optLibro.get();
        libro.setExistencias(nuevasExistencias);
        this.libroRepository.save(libro);

        RespuestaGenericaRs respuesta = new RespuestaGenericaRs();
        respuesta.setMessage("Existencias actualizadas correctamente");
        return respuesta;
    }
}

package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
<<<<<<< HEAD
import com.uniminuto.biblioteca.repository.AutorRepository;
=======
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.time.LocalDateTime;
import java.util.Collections;
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.libroService;

/**
 *
 * @author lmora
<<<<<<< HEAD
 */ // Este implemente la logica de negocio de libroservice
@Service //service admin for spring boot
public class LibroServiceImpl implements libroService {
    //Inyecta depds de autor y libro para la class
    public LibroServiceImpl(com.uniminuto.biblioteca.repository.AutorRepository AutorRepository, com.uniminuto.biblioteca.repository.LibroRepository LibroRepository) {
        this.AutorRepository = AutorRepository;
        this.LibroRepository = LibroRepository;
    }

    private final AutorRepository AutorRepository;
    private final LibroRepository LibroRepository;
    
=======
 */
@Service
public class LibroServiceImpl implements LibroService {

>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Override
<<<<<<< HEAD
    public List<Libro> obtenerListadoLibros() {
        return this.libroRepository.findAll(); // obtiene todo los libros de la bd
    }    

    @Override
    public Libro obtenerLibrosPorId(Integer id_libro) {
    Optional<Libro> libro = this.libroRepository.findById(id_libro); // por medio del optional si el libro existe retorna un .get(), si no lanza una exepción runTimeException
     if (libro.isPresent()) {
        return libro.get();
    } else {
        throw new RuntimeException("ID del libro no encontrado");
    } 
}
    
    @Override
    public List<Libro> obtenerLibrosAutorId(Integer id_autor) {
     if (!AutorRepository.existsById(id_autor)) { throw new RuntimeException("Verifique el ID. Autor no registrado");} //verifica que autor exist en la base de datos, Asi con logic inversa para no continuar con action
        List <Libro> libros = libroRepository.findByIdAutor(id_autor); //si exist busca los libros con el findByID...
        return libros;
    }

    @Override
    public Libro obtenerLibrosNombre(String titulo) {
    Optional<Libro> libro = this.libroRepository.findByTitulo(titulo);
     if (libro.isPresent()) {
        return libro.get();
    } else {
        throw new RuntimeException("El nombre del libro no existe, verifique la ortografía");
    } 
=======
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
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
    }
    
    // se verifica que los años no sean nulos && que inicioAnio >= a finAnio, si no son valida lanza la exepción
    @Override
    public List<Libro> obtenerLibrosAnion(Integer inicioAnio, Integer finAnio) {
       if (inicioAnio == null || finAnio == null || inicioAnio > finAnio) { throw new RuntimeException("El rango de años no es válido"); }else {
    List<Libro> libros = libroRepository.findByaniopublicacionBetween(inicioAnio, finAnio); //busca los libros en el rango de anio, si no hay, lanza una exepción
        if (libros.isEmpty()){
            throw new RuntimeException("No se encontraron libros en el rango de años especificado");}
        return libros;
        }
    }
    
}
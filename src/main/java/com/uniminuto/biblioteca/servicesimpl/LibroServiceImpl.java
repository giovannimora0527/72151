package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de LibroService.
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorService autorService;
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return libroRepository.findAll();
    }
    
    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = " + libroId);
        }
        return optLibro.get();
    }
    
    @Override
    public Libro obtener(Integer libroId) throws BadRequestException {
        // Delegamos al método obtenerLibroId dado que tienen el mismo comportamiento
        return obtenerLibroId(libroId);
    }
    
    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException {
        Autor autor = autorService.obtenerAutorPorId(autorId);
        return libroRepository.findByAutor(autor);
    }
    
    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        Libro libro = libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No se encuentra un libro con el título = " + nombreLibro);
        }
        return libro;
    }
    
    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(Integer anioIni, Integer anioFin) throws BadRequestException {
        return libroRepository.findByAnioPublicacionBetween(anioIni, anioFin);
    }
    
    @Override
    public RespuestaGenericaRs crearLibro(LibroRq libroRq) throws BadRequestException {
        if (libroRepository.existsByTitulo(libroRq.getTitulo())) {
            throw new BadRequestException("El libro ya está registrado.");
        }
        Libro libroGuardar = convertirLibroRqToLibro(libroRq);
        libroRepository.save(libroGuardar);
        return new RespuestaGenericaRs("Libro creado exitosamente.");
    }
    
    @Override
    public boolean estaDisponible(Integer idLibro) throws BadRequestException {
        Libro libro = obtenerLibroId(idLibro);
        int prestamosActivos = prestamoRepository.countPrestamosActivosPorLibro(idLibro);
        return libro.getExistencias() > prestamosActivos;
    }
    
    @Override
    public RespuestaGenericaRs actualizarLibro(LibroRq libroRq) throws BadRequestException {
        try {
            System.out.println("Iniciando actualización del libro con ID: " + libroRq.getId());
            
            Optional<Libro> optLibro = libroRepository.findById(libroRq.getId());
            if (!optLibro.isPresent()) {
                throw new BadRequestException("No se encuentra el libro con el id = " + libroRq.getId());
            }
            Libro libroActualizar = optLibro.get();
            
            // Validación de existencia de autor
            Autor autor = autorService.obtenerAutorPorId(libroRq.getAutorId());
            if (autor == null) {
                throw new BadRequestException("No existe el autor con ID proporcionado.");
            }
            
            // Validación de existencia de categoría
            Optional<Categoria> optCat = categoriaRepository.findById(libroRq.getCategoriaId());
            if (!optCat.isPresent()) {
                throw new BadRequestException("No existe la categoría con el ID proporcionado.");
            }
            
            // Actualización de campos
            libroActualizar.setTitulo(libroRq.getTitulo());
            libroActualizar.setAnioPublicacion(libroRq.getAnioPublicacion());
            libroActualizar.setExistencias(libroRq.getExistencias());
            libroActualizar.setAutor(autor);
            libroActualizar.setCategoria(optCat.get());
            
            // Guardar cambios
            libroRepository.save(libroActualizar);
            System.out.println("Libro actualizado exitosamente.");
            
            return new RespuestaGenericaRs("El libro se ha actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el libro: " + e.getMessage());
            throw new BadRequestException("Error interno al actualizar el libro. Verifique los datos enviados.");
        }
    }
    
    // Método auxiliar para convertir LibroRq a Libro
    private Libro convertirLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
        Libro libro = new Libro();
        libro.setAnioPublicacion(libroRq.getAnioPublicacion());
        
        // Validación de existencia de autor
        Autor autor = autorService.obtenerAutorPorId(libroRq.getAutorId());
        if (autor == null) {
            throw new BadRequestException("No existe el autor con ID proporcionado.");
        }
        
        // Validación de existencia de categoría
        Optional<Categoria> optCat = categoriaRepository.findById(libroRq.getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoría.");
        }
        
        libro.setAutor(autor);
        libro.setCategoria(optCat.get());
        libro.setTitulo(libroRq.getTitulo());
        libro.setExistencias(libroRq.getExistencias());
        return libro;
    }
}
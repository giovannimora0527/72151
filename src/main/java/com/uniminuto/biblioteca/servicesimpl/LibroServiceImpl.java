package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

  @Autowired
  private LibroRepository libroRepository;

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
  public List<Libro> obtenerLibroPorAutor(Integer autorId) {
    if (autorId == null || autorId <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de autor inválido");
    }
    List<Libro> libros = libroRepository.findByAutor_AutorId(autorId);
    if (libros == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado");
    }
    return libros;
  }

  @Override
  public Optional<Libro> obtenerLibroPorTitulo(String name) throws BadRequestException {
    if (name == null || name.trim().isEmpty()) {
      throw new BadRequestException("El titulo del libro no puede estar vacío");
    }

    Optional<Libro> libro = libroRepository.findByTitulo(name);

    if (libro.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado con el titulo: " + name);
    }

    return libro;
  }

  @Override
  public List<Libro> listarLibrosPorFecha(int anioInicio, int anioFin) {
    return libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
  }
}

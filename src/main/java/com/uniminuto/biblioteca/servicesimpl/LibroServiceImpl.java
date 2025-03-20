package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.libroService;

/**
 *
 * @author lmora
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
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
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
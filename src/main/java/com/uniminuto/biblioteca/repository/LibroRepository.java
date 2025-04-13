package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository // componente de acceso a datos en spring
public interface LibroRepository extends
        JpaRepository<Libro, Integer>  {
    
<<<<<<< HEAD
    //ALTER TABLE libros MODIFY titulo VARCHAR(255) COLLATE utf8_bin; permitir que la columna de esa tabla cuando java la consume sea sensible a mayuscula & minuscula 
    //Si no se puede acceder a la base de dato lo realizamos de esta manera
    //@Query("SELECT l FROM Libro l WHERE BINARY l.titulo = :titulo")
    Optional<Libro> findByTitulo(String titulo); //optional puede devoler un resul o estar vacio
    
    List<Libro> findByaniopublicacionBetween(Integer inicioAnio, Integer finAnio); //Between para indicarle que realice la busqueda en un rango de años, query automatic
    
    List<Libro> findByIdAutor(Integer id_autor); //busca todo los libros de un actor especifico.
    //Se agrega a la columna existencias, ya que existencia debe ser la cantida de libros asocciado al autor
=======
    /**
     * Obtiene la lista dado un autor.
     * @param autor Autor a buscar.
     * @return Lista de libros.
     */
    List<Libro> findByAutor(Autor autor);
    
    /**
     * Busca un libro por su nombre.
     * @param nombreLibro Nombre del libro a buscar.
     * @return Libro.
     */
    Libro findByTitulo(String nombreLibro);
    
    /**
     * Lista los libros por rango de fecha de publicacion.
     * @param anioIni Año inicial.
     * @param anioFin Año final.
     * @return Lista de libros que cumplen el criterio.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioIni, Integer anioFin);
    
<<<<<<< HEAD
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
}

/** JPA permite acceder a la bd a realizar consultas especificas para satifacer el metodo GET.
Metodos: 
save(Libro libro).
findById(Integer id).
deleteById(Integer id).
findAll().
**/
=======
    /**
     * Consulta el libro por titulo.
     */
    boolean existsByTitulo(String titulo);
    
}
>>>>>>> desarrollo

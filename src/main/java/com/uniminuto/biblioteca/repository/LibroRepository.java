package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository // componente de acceso a datos en spring
public interface LibroRepository extends
        JpaRepository<Libro, Integer>  {
    
    //ALTER TABLE libros MODIFY titulo VARCHAR(255) COLLATE utf8_bin; permitir que la columna de esa tabla cuando java la consume sea sensible a mayuscula & minuscula 
    //Si no se puede acceder a la base de dato lo realizamos de esta manera
    //@Query("SELECT l FROM Libro l WHERE BINARY l.titulo = :titulo")
    Optional<Libro> findByTitulo(String titulo); //optional puede devoler un resul o estar vacio
    
    List<Libro> findByaniopublicacionBetween(Integer inicioAnio, Integer finAnio); //Between para indicarle que realice la busqueda en un rango de años, query automatic
    
    List<Libro> findByIdAutor(Integer id_autor); //busca todo los libros de un actor especifico.
    //Se agrega a la columna existencias, ya que existencia debe ser la cantida de libros asocciado al autor
}

/** JPA permite acceder a la bd a realizar consultas especificas para satifacer el metodo GET.
Metodos: 
save(Libro libro).
findById(Integer id).
deleteById(Integer id).
findAll().
**/
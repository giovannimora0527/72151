package com.example.library_management.repositories;

import com.example.library_management.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Busca un libro por su título exacto (case sensitive).
     *
     * @param title el título exacto del libro
     * @return un Optional que contiene el libro si se encuentra, o vacío si no existe
     */
    Optional<Book> findByTitle(String title);

    /**
     * Obtiene una lista de libros que tienen la fecha de publicación dentro del rango dado.
     *
     * @param start la fecha de inicio
     * @param end la fecha final
     * @return una lista de libros publicados entre start y end
     */
    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate end);

    /**
     * Obtiene una lista de libros asociados a un autor.
     *
     * @param authorId el ID del autor
     * @return una lista de libros asociados al autor
     */
    List<Book> findByAuthorId(Long authorId);
}

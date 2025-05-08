
package com.example.library_management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Modelo que representa un libro.
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * Identificador único del libro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título del libro.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Fecha de publicación del libro.
     */
    @Column(nullable = false)
    private LocalDate publicationDate;

    /**
     * Autor asociado al libro.
     * Se ignoran los libros en Author para evitar recursión infinita.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties({"books"})
    private Author author;
}


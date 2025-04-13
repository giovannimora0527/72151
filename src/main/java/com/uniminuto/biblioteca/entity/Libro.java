package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "libros")
public class Libro implements Serializable {
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador único del libro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer id;
    
    /**
     * Titulo del libro.
     */
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    
    /**
     * Identificador del auto del libro.
     */
    @Column(name = "id_autor")
    private Integer idAutor;    
    /**
     * Año de publicación.
     */
    @Column(name = "anio_publicacion")
<<<<<<< HEAD
    private Integer aniopublicacion;
    
        /**
     * categoria del libro.
     */
    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;
    
    
     /**
     * cantidad de libros.
     */
    @Column(name = "existencias")
=======
    private Integer anioPublicacion;

    /** Categoría a la que pertenece el libro. */
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    /** Cantidad de ejemplares disponibles del libro. */
    @Column(name = "existencias", nullable = false)
>>>>>>> desarrollo
    private Integer existencias;
}

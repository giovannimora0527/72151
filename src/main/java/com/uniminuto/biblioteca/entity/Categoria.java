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
 * Entidad que representa una categoría dentro del sistema.
 * Se almacena en la tabla 'categoria'.
 * 
 * Cada categoría tiene un identificador único y un nombre descriptivo.
 * 
 * Tabla original en la base de datos:
 * CREATE TABLE `categoria` (
 *   `categoria_id` int(11) NOT NULL AUTO_INCREMENT,
 *   `nombre` varchar(100) NOT NULL,
 *   PRIMARY KEY (`categoria_id`)
 * )
 */
@Data
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la categoría.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer categoriaId;

    /**
     * Nombre de la categoría.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

}
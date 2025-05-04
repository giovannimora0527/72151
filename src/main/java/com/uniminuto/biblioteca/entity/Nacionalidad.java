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
 * Entidad que representa la tabla categoria en la base de datos.
 * Contiene el identificador de la categoría y su nombre.
 */
@Data
@Entity
@Table(name = "nacionalidades")
public class Nacionalidad implements Serializable {

    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nacionalidad_id")
    private Integer nacionalidadId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

}
package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Angulo
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer usuarioId;

    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Correo del usuario.
     */
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    /**
     * Telefono del usuario.
     */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /**
     * Fecha de registro del usuario.
     */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
}

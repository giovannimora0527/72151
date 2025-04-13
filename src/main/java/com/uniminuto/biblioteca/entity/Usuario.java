package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
<<<<<<< HEAD
=======
/**
 * Entidad que representa la tabla "usuarios" en la base de datos.
 */
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
<<<<<<< HEAD
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador único del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    
    /**
     * nombre
=======

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    /**
     * Nombre del usuario.
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
<<<<<<< HEAD
     * correo del usuario.
     */
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    
     /**
     * telefono.
     */
    @Column(name = "telefono")
    private String telefono;
    
    /**
     * fecha_registro.
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
=======
     * Correo electrónico del usuario (debe ser único).
     */
    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

    /**
     * Número de teléfono del usuario (opcional).
     */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /**
     * Fecha de registro del usuario (se asigna automáticamente).
     */
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
    
    /**
     * Estado del usuario Activo/Inactivo.
     */
    @Column(name = "activo")
    private Boolean activo;
<<<<<<< HEAD
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
}
=======
}
>>>>>>> desarrollo

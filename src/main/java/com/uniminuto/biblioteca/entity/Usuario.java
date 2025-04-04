package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
<<<<<<< HEAD
import java.sql.Timestamp;
=======
import java.time.LocalDateTime;
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

<<<<<<< HEAD
/** 
 *
 * @author 853345_MiguelRayo
=======
/**
 *
 * @author lmora
 */
/**
 * Entidad que representa la tabla "usuarios" en la base de datos.
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
<<<<<<< HEAD
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
=======

    private static final long serialVersionUID = 1L;

>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "id_usuario")
    private Integer usuarioId;
    
=======
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
<<<<<<< HEAD
    
    /**
     * Correo del usuario.
     */
    @Column(name = "correo", nullable = false, unique = true, length = 100)
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
    private Timestamp fecha_registro;
=======

    /**
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
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
}

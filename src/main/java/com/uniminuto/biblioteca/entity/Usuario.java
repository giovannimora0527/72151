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
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
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
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
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
}

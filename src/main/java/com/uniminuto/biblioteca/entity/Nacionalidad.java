package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "nacionalidad")
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nacionalidad_id")
    private Integer nacionalidadId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}


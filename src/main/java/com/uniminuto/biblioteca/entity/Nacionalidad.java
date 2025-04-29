package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "nacionalidad")
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nacionalidad_id") // ← corregido aquí
    private Integer nacionalidadId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}

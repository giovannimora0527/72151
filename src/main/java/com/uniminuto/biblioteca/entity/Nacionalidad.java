package com.uniminuto.biblioteca.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "nacionalidades")
public class Nacionalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nacionalidad")
    private Integer nacionalidadId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}

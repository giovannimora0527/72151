/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author holma
 *
 * Entidad que representa una nacionalidad dentro del sistema.
 * Se almacena en la tabla 'nacionalidad'.
 * 
 * Cada nacionalidad tiene un identificador único y un nombre descriptivo.
 * 
 * Tabla original en la base de datos:
 * CREATE TABLE `nacionalidad` (
 *   `nacionalidad_id` int(11) NOT NULL AUTO_INCREMENT,
 *   `nombre` varchar(100) NOT NULL,
 *   PRIMARY KEY (`nacionalidad_id`)
 * )
 */
@Data
@Entity
@Table(name = "nacionalidad")
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la nacionalidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nacionalidad_id")
    private Integer  nacionalidadId;

    /**
     * Nombre de la nacionalidad.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
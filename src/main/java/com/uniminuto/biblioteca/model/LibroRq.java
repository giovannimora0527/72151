package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class LibroRq {
    private Integer id; // Se agrega el ID para la actualización
    private String titulo;
    private Integer autorId;
    private Integer categoriaId;
    private Integer existencias;
    private Integer anioPublicacion;
     private String fechaEntrega;
}
package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class LibroRq {

    private Integer anioPublicacion;
    private Integer autorId;
    private Integer categoriaId;
    private Integer existencias;
    private String titulo;

}

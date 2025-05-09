package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class PrestamoRq {
        private Integer idusuario;
        private Integer idlibro;
        private String fechaDevolucion;
}
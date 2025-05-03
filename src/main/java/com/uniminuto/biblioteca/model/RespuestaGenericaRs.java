package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class RespuestaGenericaRs {
    private String message;

    // Constructor sin argumentos (necesario para JPA/Lombok)
    public RespuestaGenericaRs() {
    }

    // Constructor con mensaje
    public RespuestaGenericaRs(String message) {
        this.message = message;
    }
}
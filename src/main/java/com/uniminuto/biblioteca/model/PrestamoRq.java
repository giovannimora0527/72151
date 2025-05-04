package com.uniminuto.biblioteca.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class PrestamoRq {
    private Integer autorId;
    private Integer libroId;
    private LocalDateTime fechaDevolucio;
}

package com.uniminuto.biblioteca.model;

import com.uniminuto.biblioteca.entity.Deuda;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author santiago
 */
@Data
public class ActualizarDeudaRq {
    private Integer idDeuda;
    private Deuda.MetodoPago metodoPago;
    private LocalDateTime fechaPago;
}

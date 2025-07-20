package com.ba_aprende.ecommerce.dto.linea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LineaDtoResponse {

    private Long productoId;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
}

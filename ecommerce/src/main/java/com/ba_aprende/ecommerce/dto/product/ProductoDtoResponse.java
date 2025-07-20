package com.ba_aprende.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDtoResponse {
    Long id;
    String nombre;
    String descripcion;
    double precio;
    String categoria;
    String imagenUrl;
    int stock;
}

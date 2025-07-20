package com.ba_aprende.ecommerce.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductoUpdateDto {
    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long id;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El stock no puede ser nulo")
    @Positive(message = "El stock debe ser mayor a 0")
    private Integer stock;
}

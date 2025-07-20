package com.ba_aprende.ecommerce.dto.pedido;

import com.ba_aprende.ecommerce.dto.linea.LineaDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PedidoDto {
    @NotNull(message = "El id del cliente no puede ser nulo")
    @Positive
    long idCliente;
    @NotNull(message = "La lista de líneas no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos una línea en el pedido")
    @Valid
    private List<LineaDto> lineas=new ArrayList<>();
}

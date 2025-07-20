package com.ba_aprende.ecommerce.dto.pedido;

import com.ba_aprende.ecommerce.dto.linea.LineaDtoResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDtoResponse {
    private Long id;
    private List<LineaDtoResponse> lineas=new ArrayList<>();
    private LocalDate fecha;

}

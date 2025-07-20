package com.ba_aprende.ecommerce.dto.Cliente;

import com.ba_aprende.ecommerce.dto.pedido.PedidoDtoResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ClienteDtoResponse {
    long id;
    String nombre;
    String apellido;
}

package com.ba_aprende.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "linea_pedido")
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Producto producto;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

}

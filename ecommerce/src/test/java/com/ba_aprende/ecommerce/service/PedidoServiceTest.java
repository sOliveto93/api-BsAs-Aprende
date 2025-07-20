package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.linea.LineaDto;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDto;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDtoResponse;
import com.ba_aprende.ecommerce.entity.Cliente;
import com.ba_aprende.ecommerce.entity.LineaPedido;
import com.ba_aprende.ecommerce.entity.Pedido;
import com.ba_aprende.ecommerce.entity.Producto;
import com.ba_aprende.ecommerce.exception.ClienteInactivoException;
import com.ba_aprende.ecommerce.exception.StockInsuficienteException;
import com.ba_aprende.ecommerce.repository.ClienteRepository;
import com.ba_aprende.ecommerce.repository.PedidoRepository;
import com.ba_aprende.ecommerce.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;


import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductoService productoService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private PedidoService pedidoService;

    private Cliente clienteActivo;
    private Producto productoConStock;
    private PedidoDto requestDto;

    @BeforeEach
    public void setup(){
        clienteActivo = Cliente.builder().id(1L).nombre("usuario Prueba activo").apellido("prueba").activo(true).dni(11111111).build();
        productoConStock = Producto.builder().id(1L).nombre("prueba").precio(60.5).descripcion("producto prueba con stock").categoria("prueba").imagenUrl("https://res.cloudinary.com/dd8s5hozt/image/upload/v1752973130/Notebook_15_pulgadas_i5_8GB_RAM_ijyhmb.webp").stock(10).build();
        LineaDto lineaDto= LineaDto.builder().productoId(1L).cantidad(2).build();
        requestDto=PedidoDto.builder().idCliente(1L).lineas(List.of(lineaDto)).build();

    }

    @Test
    public void crearPedido_deberiaCrearPedidoSiTodoEsValido(){
        when(clienteService.verificarCliente(1L)).thenReturn(clienteActivo);
        when(productoService.getProductoById(1L)).thenReturn(productoConStock);
        when(productoService.hayStock(any())).thenReturn(true);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(new Pedido());

        PedidoDtoResponse response=pedidoService.crearPedido(requestDto);

        assertNotNull(response);
        verify(pedidoRepository).save(any(Pedido.class));
    }
    @Test
    public void crearPedido_deberiaLanzarExcepcionSiNoHayStock() {
        when(productoService.getProductoById(1L)).thenReturn(productoConStock);
        when(clienteService.verificarCliente(1L)).thenReturn(clienteActivo);
        when(productoService.hayStock(any(LineaPedido.class))).thenReturn(false);
        //nunca llega por la exception pero pertenece al flujo del metodo
        assertThrows(StockInsuficienteException.class, () -> {
            pedidoService.crearPedido(requestDto);
        });

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
    @Test
    public void crearPedido_verificaCorrectoManejoClienteInactivoException() {
        when(productoService.getProductoById(1L)).thenReturn(productoConStock);
        //forzamos para ver si maneja bien la exception el metodo
        //no estamos probando la logica
        when(clienteService.verificarCliente(1L)).thenThrow(new ClienteInactivoException("Cliente con el id 1 esta dado de baja. No puede registrar pedidos"));
        lenient().when(productoService.hayStock(any(LineaPedido.class))).thenReturn(true);

        assertThrows(ClienteInactivoException.class,()->{
            pedidoService.crearPedido(requestDto);
        });

        verify(pedidoRepository,never()).save(any(Pedido.class));
    }
}

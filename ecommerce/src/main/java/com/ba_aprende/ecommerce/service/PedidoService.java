package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.linea.LineaDto;
import com.ba_aprende.ecommerce.dto.linea.LineaDtoResponse;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDto;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDtoResponse;
import com.ba_aprende.ecommerce.entity.Cliente;
import com.ba_aprende.ecommerce.entity.LineaPedido;
import com.ba_aprende.ecommerce.entity.Pedido;
import com.ba_aprende.ecommerce.entity.Producto;
import com.ba_aprende.ecommerce.exception.ClienteNotFoundException;
import com.ba_aprende.ecommerce.exception.PedidoNotFoundException;
import com.ba_aprende.ecommerce.exception.StockInsuficienteException;
import com.ba_aprende.ecommerce.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    PedidoRepository pedidoRepository;
    ProductoService productoService;
    ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository, ProductoService productoService, ClienteService clienteService){
        this.pedidoRepository =pedidoRepository;
        this.productoService=productoService;
        this.clienteService=clienteService;
    }

    public PedidoDtoResponse getById(long id){
        Pedido pedido= pedidoRepository
                .findById(id)
                .orElseThrow(()->new PedidoNotFoundException("Pedido con el id "+id+" no existe"));
        return pedidoToDto(pedido);
    }

    public PedidoDtoResponse pedidoToDto(Pedido pedido){
        PedidoDtoResponse dto=new PedidoDtoResponse();
        List<LineaDtoResponse> lineasDtoResponse = new ArrayList<>();

        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());

        pedido.getLineas()
                .forEach(lineaPedido -> {
                    LineaDtoResponse lineaDto = new LineaDtoResponse();

                    lineaDto.setProductoId(lineaPedido.getProducto().getId());
                    lineaDto.setCantidad(lineaPedido.getCantidad());
                    lineaDto.setPrecioUnitario(lineaPedido.getPrecioUnitario());
                    lineaDto.setSubtotal(lineaPedido.getSubTotal());

                    lineasDtoResponse.add(lineaDto);
                });
        dto.setLineas(lineasDtoResponse);
        return dto;
    }

    public List<PedidoDtoResponse> getAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream().map(pedido -> pedidoToDto(pedido)).toList();
    }

    public PedidoDtoResponse deleteById(Long id) {
        Pedido pedido= pedidoRepository
                .findById(id)
                .orElseThrow(()->new PedidoNotFoundException("Pedido con el id "+id+" no existe"));
        //en este orden por posibles problemas con jpa al eliminar el pedido
        PedidoDtoResponse dto=pedidoToDto(pedido);

        pedidoRepository.deleteById(pedido.getId());
        return dto;
    }
    @Transactional
    public PedidoDtoResponse crearPedido(PedidoDto pedido) {
       Pedido nuevoPedido=new Pedido();


       List<LineaPedido>lineasPedido=pedido.getLineas().stream()
               .map(lineaDto -> lineaDtoToLinea(lineaDto,nuevoPedido))
               .toList();
        //verificamos stock antes que nada
        // (a tener en cuenta.. puede ser antes de la conversion a entidad)
        Cliente cliente=verficarCliente(pedido.getIdCliente());
        verificarStock(lineasPedido);

        nuevoPedido.setCliente(cliente);
        nuevoPedido.setLineas(lineasPedido);
        nuevoPedido.setFecha(LocalDate.now());
        pedidoRepository.save(nuevoPedido);
        return pedidoToDto(nuevoPedido);
    }
    public List<PedidoDtoResponse> getPedidosByClienteId(long clienteId) {
        Cliente cliente = clienteService.verificarCliente(clienteId);
        List<Pedido> pedidos = cliente.getPedidos();
        return pedidos.stream().map(pedido->pedidoToDto(pedido)).toList();
    }



    public LineaPedido lineaDtoToLinea(LineaDto lineaDto,Pedido nuevoPedido){
        LineaPedido linea=new LineaPedido();
        Producto producto=productoService.getProductoById(lineaDto.getProductoId());

        linea.setPedido(nuevoPedido);
        linea.setProducto(producto);
        linea.setCantidad(lineaDto.getCantidad());
        linea.setPrecioUnitario(producto.getPrecio());
        linea.setSubTotal(producto.getPrecio() * lineaDto.getCantidad());
    return linea;
    }

    public void verificarStock(List<LineaPedido> lineasPedido){
        lineasPedido.stream()
                .filter(linea ->!productoService.hayStock(linea))
                .findFirst()
                .ifPresent(linea->{
                    throw new StockInsuficienteException("hay faltante de stock del producto "+linea.getProducto().getNombre());
                });

        lineasPedido.forEach(linea->productoService.descontarStock(linea));

    }
    public Cliente verficarCliente(long id){
        return clienteService.verificarCliente(id);
    }
}

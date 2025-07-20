package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.product.ProductoDtoResponse;
import com.ba_aprende.ecommerce.dto.product.ProductoUpdateDto;
import com.ba_aprende.ecommerce.entity.LineaPedido;
import com.ba_aprende.ecommerce.entity.Producto;
import com.ba_aprende.ecommerce.exception.ProductoNoFoundException;
import com.ba_aprende.ecommerce.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoService {

    ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<ProductoDtoResponse> getAll(){
       List<Producto> productos=productoRepository.findAll();

      return productos.stream()
               .map(producto -> {
                   return productToDto(producto);
               })
               .toList();

    }
    public ProductoDtoResponse getProductoDtoById(long id){
        Producto producto= productoRepository
                .findById(id)
                .orElseThrow(()->new ProductoNoFoundException("Producto con id " + id + " no encontrado"));
        return productToDto(producto);

    }
    public Producto getProductoById(long id){
        Producto producto= productoRepository
                .findById(id)
                .orElseThrow(()->new ProductoNoFoundException("Producto con id " + id + " no encontrado"));
        return producto;
    }

    public ProductoDtoResponse getByName(String nombre){
         Producto producto=productoRepository
                .findByNombre(nombre)
                .orElseThrow(()->new ProductoNoFoundException("Producto con nombre " + nombre + " no encontrado"));
        return productToDto(producto);
    }
    public ProductoDtoResponse deleteById(long id){
        Producto producto = productoRepository
                .findById(id)
                .orElseThrow(() -> new ProductoNoFoundException("Producto con id " + id + " no encontrado"));

        productoRepository.deleteById(id);

        return productToDto(producto);
    }
    public boolean hayStock(LineaPedido linea){
        Producto producto=linea.getProducto();
        int stock = producto.getStock();
        int cantidad = linea.getCantidad();
        return stock >= cantidad;
    }
    public void descontarStock(LineaPedido linea){
        Producto producto=linea.getProducto();
        int stock = producto.getStock();
        int cantidad = linea.getCantidad();
        producto.setStock(stock-cantidad);
        productoRepository.save(producto);
    }

    public ProductoDtoResponse update(ProductoUpdateDto dto){

        Producto producto= productoRepository
                .findById(dto.getId())
                .orElseThrow(()->new ProductoNoFoundException("Producto con id " + dto.getId() + " no encontrado"));

        boolean actualizado = false;
//por si cambio los primitivos o le quito validaciones por algun motivo
        if (!Objects.equals(producto.getPrecio(), dto.getPrecio())) {
            producto.setPrecio(dto.getPrecio());
            actualizado=true;
        }
        if (!Objects.equals(producto.getStock(), dto.getStock())) {
            producto.setStock(dto.getStock());
            actualizado=true;
        }

        if(actualizado){
            Producto newProducto=productoRepository.save(producto);
            return productToDto(newProducto);
        }
        return productToDto(producto);
    }

    ProductoDtoResponse productToDto(Producto producto){
        ProductoDtoResponse dto= new ProductoDtoResponse();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(producto.getCategoria());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setStock(producto.getStock());

        return dto;
    }
}

package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.product.ProductoCreateDto;
import com.ba_aprende.ecommerce.dto.product.ProductoDtoResponse;
import com.ba_aprende.ecommerce.entity.Producto;
import com.ba_aprende.ecommerce.exception.ProductoDuplicadoException;
import com.ba_aprende.ecommerce.exception.ProductoNoFoundException;
import com.ba_aprende.ecommerce.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    ProductoRepository productoRepository;

    @InjectMocks
    ProductoService productoService;

    Producto producto;
    ProductoCreateDto createDto;
    @BeforeEach
    public void setup(){
        producto=Producto.builder()
                .id(1L)
                .nombre("Mouse")
                .precio(5000)
                .descripcion("Mouse gamer")
                .categoria("Periferico")
                .imagenUrl("http://imagen.jpg")
                .stock(10)
                .build();
        createDto = ProductoCreateDto.builder()
                .nombre("Monitor")
                .precio(12000.0)
                .stock(5)
                .categoria("Periferico")
                .descripcion("Pantalla 24''")
                .imagenUrl("https://url.jpg")
                .build();
    }

    @Test
    public void getAll_retornaListaProductoDto(){
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<ProductoDtoResponse> productos=productoService.getAll();

        assertEquals(1,productos.size());
        assertEquals("Mouse",productos.get(0).getNombre());
    }
    @Test
    void getByName_retornarProductoDto_siExiste() {
        when(productoRepository.findByNombre(producto.getNombre())).thenReturn(Optional.of(producto));

        ProductoDtoResponse dto=productoService.getByName(producto.getNombre());

        assertEquals(producto.getNombre(),dto.getNombre());
    }
    @Test
    void getByName_lanzarExcepcion_siNoExiste() {
        when(productoRepository.findByNombre(producto.getNombre())).thenReturn(Optional.empty());

        assertThrows(ProductoNoFoundException.class, () -> {
            productoService.getByName(producto.getNombre());
        });
    }
    @Test
    void create_crearProductoNuevo_siNoExiste() {
        when(productoRepository.findByNombre(createDto.getNombre())).thenReturn(Optional.empty());
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> {
            Producto p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProductoDtoResponse response = productoService.create(createDto);

        assertEquals(createDto.getNombre(), response.getNombre());
        assertEquals(createDto.getPrecio(), response.getPrecio());
    }
    @Test
    void create_lanzarExcepcion_siYaExisteProductoConEseNombre() {
       when(productoRepository.findByNombre(createDto.getNombre())).thenReturn(Optional.of(new Producto()));

        assertThrows(ProductoDuplicadoException.class, () -> {
            productoService.create(createDto);
        });
    }
    @Test
    void deleteById_eliminarProducto_siExiste() {
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));

        ProductoDtoResponse dto = productoService.deleteById(producto.getId());

        verify(productoRepository).deleteById(producto.getId());
        assertEquals(producto.getNombre(), dto.getNombre());
    }

}

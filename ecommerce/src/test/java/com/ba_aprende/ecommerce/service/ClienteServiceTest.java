package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.Cliente.ClienteDto;
import com.ba_aprende.ecommerce.dto.Cliente.ClienteDtoResponse;
import com.ba_aprende.ecommerce.entity.Cliente;
import com.ba_aprende.ecommerce.exception.ClienteInactivoException;
import com.ba_aprende.ecommerce.exception.ClienteNotFoundException;
import com.ba_aprende.ecommerce.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteActivo;
    private Cliente clienteInactivo;

    @BeforeEach
    public void setup() {
        clienteActivo = Cliente.builder().id(1L).nombre("usuario Prueba activo").apellido("prueba").activo(true).dni(11111111).build();
        clienteInactivo = Cliente.builder().id(2L).nombre("usuario Prueba inactivo").apellido("prueba").activo(false).dni(22222222).build();
    }

    @Test
    public void verificarCliente_retornarClienteSiExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteActivo));
        Cliente resultado = clienteService.verificarCliente(1L);
        assertNotNull(resultado);
        assertEquals(clienteActivo.getId(), resultado.getId());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void verificarCliente_lanzarClienteNotFoundSiNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.verificarCliente(99L);
        });
    }

    @Test
    public void verificarCliente_lanzarClienteInactivoExceptionSiNoActivo() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.of(clienteInactivo));

        assertThrows(ClienteInactivoException.class, () -> {
            clienteService.verificarCliente(2L);
        });
    }

    @Test
    public void getById_retornaDtoSiExiste(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteActivo));
        ClienteDtoResponse dto= clienteService.getById(1L);
        assertEquals("usuario Prueba activo",dto.getNombre());
    }
    @Test
    public void getById_lanzarExcepcionSiNoExiste(){
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class,()->clienteService.getById(1L));
    }

    @Test
    public void getByDni_retornarDtoSiExiste(){
        when(clienteRepository.findByDni(11111111)).thenReturn(Optional.of(clienteActivo));
        ClienteDtoResponse dto=clienteService.getByDni(11111111);
        assertEquals("usuario Prueba activo",dto.getNombre());
    }

    @Test
    public void getByDni_lanzarExcepcionSiNoExiste() {
        when(clienteRepository.findByDni(11111111)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class,()->clienteService.getByDni(11111111));
    }

    @Test
    public void crearCliente_CrearClienteSiNoExiste(){
        ClienteDto dto = new ClienteDto("Juan", "Perez", 99999999);
        when(clienteRepository.findByDni(dto.getDni())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(arg -> {
            Cliente cliente = arg.getArgument(0);
            cliente.setId(1L);
            return cliente;
        });
        ClienteDtoResponse response=clienteService.crearCliente(dto);
        assertEquals(1,response.getId());
        assertEquals(dto.getNombre(),response.getNombre());
        assertEquals(dto.getApellido(),response.getApellido());
    }
    @Test
    public void crearCliente_lanzarExceptionSiYaExiste(){
        ClienteDto dto = new ClienteDto("Juan", "Perez", 99999999);
        when(clienteRepository.findByDni(dto.getDni())).thenReturn(Optional.of(clienteActivo));
        assertThrows(IllegalArgumentException.class,()->clienteService.crearCliente(dto));
    }

}


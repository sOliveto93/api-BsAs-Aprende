package com.ba_aprende.ecommerce.service;

import com.ba_aprende.ecommerce.dto.Cliente.ClienteDto;
import com.ba_aprende.ecommerce.dto.Cliente.ClienteDtoResponse;
import com.ba_aprende.ecommerce.entity.Cliente;
import com.ba_aprende.ecommerce.exception.ClienteInactivoException;
import com.ba_aprende.ecommerce.exception.ClienteNotFoundException;
import com.ba_aprende.ecommerce.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository=clienteRepository;
    }

    public ClienteDtoResponse getById(long id){
        Cliente cliente=clienteRepository.findById(id).orElseThrow(()->new ClienteNotFoundException("Cliente con el id "+id+" no existe") );
        return clienteToDto(cliente);
    }
    public Cliente verificarCliente(long id){
        Cliente cliente=clienteRepository.findById(id).orElseThrow(()->new ClienteNotFoundException("Cliente con el id "+id+" no existe") );
        if(!cliente.isActivo()){
            throw new ClienteInactivoException("Cliente con el id "+id+" esta dado de baja. No puede registrar pedidos");
        }
        return cliente;
    }
    public ClienteDtoResponse getByDni(int dni){
        Cliente cliente=clienteRepository.findByDni(dni).orElseThrow(()->new ClienteNotFoundException("Cliente con el dni "+dni+" no existe"));
        return clienteToDto(cliente);
    }
    public List<ClienteDtoResponse> getAll(){
        List<Cliente> clientes=clienteRepository.findAll();
        return clientes.stream().map(cliente -> clienteToDto(cliente)).toList();
    }


    public ClienteDtoResponse crearCliente(ClienteDto clienteDto){
        if(existeCliente(clienteDto.getDni())){
            throw new IllegalArgumentException("Cliente con el dni "+clienteDto.getDni()+" ya fue registrado");
        }
        Cliente nuevoCliente=new Cliente();
        nuevoCliente.setNombre(clienteDto.getNombre());
        nuevoCliente.setApellido(clienteDto.getApellido());
        nuevoCliente.setDni(clienteDto.getDni());
        nuevoCliente.setActivo(true);

        return clienteToDto(clienteRepository.save(nuevoCliente));
    }
    public ClienteDtoResponse desactivarClienteById(long id){
        Cliente cliente=clienteRepository.findById(id).orElseThrow(()->new ClienteNotFoundException("Cliente con el id "+id+" no existe"));
        cliente.setActivo(false);
        return clienteToDto(clienteRepository.save(cliente));
    }

    public ClienteDtoResponse clienteToDto(Cliente cliente){
        ClienteDtoResponse dto= new ClienteDtoResponse();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        return dto;
    }
    public boolean existeCliente(int dni){
        return clienteRepository.findByDni(dni).isPresent();
    }
}

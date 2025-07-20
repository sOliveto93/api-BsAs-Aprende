package com.ba_aprende.ecommerce.controller;

import com.ba_aprende.ecommerce.dto.Cliente.ClienteDto;
import com.ba_aprende.ecommerce.dto.Cliente.ClienteDtoResponse;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDtoResponse;
import com.ba_aprende.ecommerce.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    @GetMapping("/getAll")
    public List<ClienteDtoResponse> getAll(){
        return clienteService.getAll();
    }
    @GetMapping("/getById/{id}")
    public ClienteDtoResponse getById(@PathVariable long id){
        return clienteService.getById(id);
    }
    @GetMapping("/getByDni/{dni}")
    public ClienteDtoResponse getById(@PathVariable int dni){
        return clienteService.getByDni(dni);
    }
    @PostMapping("/create")
    public ClienteDtoResponse getById(@Valid @RequestBody ClienteDto dto){
        return clienteService.crearCliente(dto);
    }
    @DeleteMapping("/deleteById/{id}")
    public ClienteDtoResponse deleteById(@PathVariable long id){
        return clienteService.desactivarClienteById(id);
    }
}

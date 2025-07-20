package com.ba_aprende.ecommerce.controller;

import com.ba_aprende.ecommerce.dto.pedido.PedidoDto;
import com.ba_aprende.ecommerce.dto.pedido.PedidoDtoResponse;
import com.ba_aprende.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService=pedidoService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PedidoDtoResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok().body(pedidoService.getById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PedidoDtoResponse>> getAll(){
        return ResponseEntity.ok().body(pedidoService.getAll());
    }
    @GetMapping("/getPedidosByCliente/{id}")
    public List<PedidoDtoResponse> getPedidos(@PathVariable long id){
        return pedidoService.getPedidosByClienteId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<PedidoDtoResponse> create(@Valid @RequestBody PedidoDto pedido){
        PedidoDtoResponse creado=pedidoService.crearPedido(pedido);
        URI location= URI.create("/pedido/getById/"+creado.getId());

        return ResponseEntity.created(location).body(creado);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<PedidoDtoResponse> deleteById(@PathVariable Long id){
        return ResponseEntity.ok().body(pedidoService.deleteById(id));
    }
}

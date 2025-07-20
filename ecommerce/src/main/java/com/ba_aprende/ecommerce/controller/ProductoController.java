package com.ba_aprende.ecommerce.controller;

import com.ba_aprende.ecommerce.dto.product.ProductoDtoResponse;
import com.ba_aprende.ecommerce.dto.product.ProductoUpdateDto;
import com.ba_aprende.ecommerce.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService=productoService;
    }

    @GetMapping("/getAll")
    ResponseEntity<List<ProductoDtoResponse>> getAll(){
        return ResponseEntity.ok().body(productoService.getAll());
    }
    @GetMapping("/getById/{id}")
    ResponseEntity<ProductoDtoResponse> getAll(@PathVariable long id){
        return ResponseEntity.ok().body(productoService.getProductoDtoById(id));
    }
    @GetMapping("/getByName")
    ResponseEntity<ProductoDtoResponse> getByName(@RequestParam String nombre){
        return ResponseEntity.ok().body(productoService.getByName(nombre));
    }
    @DeleteMapping("/deleteById/{id}")
    ResponseEntity<ProductoDtoResponse> deleteById(@PathVariable long id){
        return ResponseEntity.ok().body(productoService.deleteById(id));
    }
    @PutMapping("/updateProduct")
    ResponseEntity<ProductoDtoResponse> updateProduct(@Valid @RequestBody ProductoUpdateDto dto){
        return ResponseEntity.ok().body(productoService.update(dto));
    }
}

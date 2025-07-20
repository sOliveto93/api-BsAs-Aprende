package com.ba_aprende.ecommerce.exception;

public class PedidoNotFoundException extends RuntimeException{
    public PedidoNotFoundException(String message){
        super(message);
    }
}

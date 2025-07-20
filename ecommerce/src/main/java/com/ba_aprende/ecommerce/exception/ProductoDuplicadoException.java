package com.ba_aprende.ecommerce.exception;

public class ProductoDuplicadoException extends RuntimeException{
    public ProductoDuplicadoException(String message){
        super(message);
    }
}

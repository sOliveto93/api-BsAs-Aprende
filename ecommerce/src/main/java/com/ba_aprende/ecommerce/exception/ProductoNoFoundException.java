package com.ba_aprende.ecommerce.exception;

public class ProductoNoFoundException extends RuntimeException{
    public ProductoNoFoundException(String message) {
        super(message);
    }
}

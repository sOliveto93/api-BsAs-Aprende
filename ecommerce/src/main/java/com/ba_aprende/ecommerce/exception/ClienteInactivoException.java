package com.ba_aprende.ecommerce.exception;

public class ClienteInactivoException extends RuntimeException{
    public ClienteInactivoException(String message){
        super(message);
    }
}

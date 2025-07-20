package com.ba_aprende.ecommerce.exception;

public class StockInsuficienteException extends RuntimeException{

    public  StockInsuficienteException(String message){
        super(message);
    }
}

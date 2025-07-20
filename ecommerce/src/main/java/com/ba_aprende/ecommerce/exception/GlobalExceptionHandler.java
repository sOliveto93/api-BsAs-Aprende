package com.ba_aprende.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductoNoFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductoNotFoundException(ProductoNoFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage(),404,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(ProductoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleProductoDuplicadoException(ProductoDuplicadoException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage(),409,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNotFoundException(PedidoNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage(),404, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleStockInsuficienteException(StockInsuficienteException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage(),400,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClienteNotFoundException(ClienteNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage(),404,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(ClienteInactivoException.class)
    public ResponseEntity<ErrorResponse> handleClienteInactivoException(ClienteInactivoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 403,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Ruta no encontrada", 404, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Error interno del servidor", 500,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

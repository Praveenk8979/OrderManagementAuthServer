package com.ordermanagement.OrderManagementAndAuthServer.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionHandling.OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStock(ExceptionHandling.OutOfStockException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandling.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(ExceptionHandling.UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandling.OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFound(ExceptionHandling.OrderNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandling.InvalidOrderOperationException.class)
    public ResponseEntity<String> handleInvalidOperation(ExceptionHandling.InvalidOrderOperationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong: " + ex.getMessage());
    }
}
